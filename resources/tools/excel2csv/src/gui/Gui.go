package gui

import (
	"config"
	"entry"
	"excel"
	"fmt"
	"fyne.io/fyne"
	"fyne.io/fyne/app"
	"fyne.io/fyne/dialog"
	"fyne.io/fyne/layout"
	"fyne.io/fyne/theme"
	"fyne.io/fyne/widget"
	"general"
	pool2 "pool"
	"strings"
	"sync"
)

type toolGui struct {
	// gui组件
	app      fyne.App
	window   fyne.Window
	allCheck []*widget.Check
	form     *widget.Form
	boxes    []*widget.Box
	labels   [][]*widget.Label
	// 缓存表数据
	tables map[string]*entry.Table
	Pool   *pool2.Pool
}

// 单例
var instance *toolGui
var once sync.Once

func Instance() *toolGui {
	once.Do(func() {
		instance = &toolGui{}
	})
	return instance
}

func (gui *toolGui) Init() {
	gui.Pool = pool2.NewPool(4)
	go gui.Pool.Run()
	go func() {
		for logInfo := range gui.Pool.LogChannel {
			gui.Log(logInfo)
		}
	}()
}

func (gui *toolGui) Run() bool {
	gui.app = app.New()
	gui.app.Settings().SetTheme(theme.LightTheme())
	gui.window = gui.app.NewWindow("ExcelTool")
	// 左边
	left := widget.NewVBox()
	files, err := excel.GetAllExcelInDir(config.EXCEL_DIR)
	if err != nil {
		gui.Error(err)
	}
	for _, file := range files {
		check := widget.NewCheck(file, func(b bool) {
			gui.freshForm()
		})
		gui.allCheck = append(gui.allCheck, check)
		left.Append(check)
	}

	// 右边
	right := widget.NewVBox()
	gui.form = widget.NewForm()
	right.Append(gui.form)

	content := fyne.NewContainerWithLayout(layout.NewHBoxLayout(), left, right)
	gui.window.SetMainMenu(fyne.NewMainMenu(fyne.NewMenu("file", fyne.NewMenuItem("general", func() {
		gui.general()
	}))))
	gui.window.SetContent(content)
	gui.window.Resize(fyne.NewSize(480, 320))
	gui.window.ShowAndRun()
	return true
}

func (gui *toolGui) general() {
	var selectFiles []string
	for _, check := range gui.allCheck {
		if check.Checked {
			selectFiles = append(selectFiles, check.Text)
		}
	}
	if len(selectFiles) <= 0 {
		return
	}
	gui.GeneralFiles(selectFiles)
}

func (gui *toolGui) freshForm() {
	for _, check := range gui.allCheck {
		if check.Focused() {
			table := gui.getTable(check.Text)
			if table == nil {
				gui.Log("to table failed! table is nil")
			}
			gui.clearForm()
			for i, column := range table.Columns {
				if len(gui.form.Items) <= i {
					label1 := widget.NewLabel(column.Name)
					label2 := widget.NewLabel(column.JavaType)
					box := widget.NewHBox(label1, label2)
					gui.form.Append("", box)
					gui.boxes = append(gui.boxes, box)
					gui.labels = append(gui.labels, []*widget.Label{label1, label2})
				} else {
					gui.labels[i][0].SetText(column.Name)
					gui.labels[i][1].SetText(column.JavaType)
				}
			}
			return
		}
	}
}

func (gui *toolGui) clearForm() {
	for _, labels := range gui.labels {
		labels[0].SetText("")
		labels[1].SetText("")
	}
}

func (gui *toolGui) getTable(fileName string) *entry.Table {
	if gui.tables == nil {
		gui.tables = make(map[string]*entry.Table)
	}
	if gui.tables[fileName] == nil {
		gui.tables[fileName], _ = excel.ExcelToTable(config.EXCEL_DIR + fileName)
	}
	return gui.tables[fileName]
}

func (gui *toolGui) Log(info string) {
	dialog.ShowInformation("tips", info, gui.window)
	fmt.Print(info)
}

func (gui *toolGui) Error(err error) {
	dialog.ShowError(err, gui.window)
	fmt.Print(err.Error())
}

/**处理已选择文件***********************/
func (gui *toolGui) GeneralFiles(files []string) {
	for _, file := range files {
		gui.processFile(file)
	}
	gui.Log("general success: " + strings.Join(files, ","))
}

func (gui *toolGui) processFile(fileName string) {
	table := gui.getTable(fileName)
	if table == nil {
		gui.Log("to table failed! table is nil")
		return
	}
	//修改成在协程池中执行
	//general.GeneralToJavaBean(table)
	//general.GeneralToCsv(table)
	t := pool2.NewTask(func() error {
		err := general.GeneralToJavaBean(table)
		if err != nil {
			gui.Error(err)
		}
		err = general.GeneralToMgr(table)
		if err != nil {
			gui.Error(err)
		}
		err = general.GeneralToCsv(table)
		if err != nil {
			gui.Error(err)
		}
		return nil
	})
	gui.Pool.AddTask(t)
}

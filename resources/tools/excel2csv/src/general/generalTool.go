package general

import (
	"config"
	"encoding/csv"
	"entry"
	"os"
	"strings"
	"text/template"
	"time"
)

func GeneralToJavaBean(table *entry.Table) error {
	funcs := template.FuncMap{"getDate": GetDate}
	tmpl, err := template.New("bean.tmpl").Funcs(funcs).ParseFiles(config.TMPL_DIR + "bean.tmpl")
	if err != nil {
		return err
	}
	newBeanFileName := strings.Join([]string{table.JavaName + "Bean", "java"}, ".")
	filePath := config.JAVA_BEAN_DIR + newBeanFileName
	iWriter, err := os.OpenFile(filePath, os.O_RDWR|os.O_CREATE|os.O_TRUNC, 0666)
	if err != nil {
		return err
	}
	defer iWriter.Close()
	err = tmpl.Execute(iWriter, table)
	if err != nil {
		return err
	}
	return nil
}

func GeneralToMgr(table *entry.Table) error {
	tmpl, err := template.New("mgr.tmpl").ParseFiles(config.TMPL_DIR + "mgr.tmpl")
	if err != nil {
		return err
	}
	newMgrFileName := strings.Join([]string{table.JavaName + "TempMgr", "java"}, ".")
	filePath := config.JAVA_TEMPLATE_DIR + newMgrFileName
	iWriter, err := os.OpenFile(filePath, os.O_RDWR|os.O_CREATE|os.O_TRUNC, 0666)
	if err != nil {
		return err
	}
	defer iWriter.Close()
	err = tmpl.Execute(iWriter, table)
	if err != nil {
		return err
	}
	return nil
}

func GeneralToCsv(table *entry.Table) error {
	newFileName := strings.Join([]string{table.Name, "csv"}, ".")
	filePath := config.CSV_DIR + newFileName
	iWriter, err := os.OpenFile(filePath, os.O_RDWR|os.O_CREATE|os.O_TRUNC, 0666)
	if err != nil {
		return err
	}
	defer iWriter.Close()
	//iWriter.Seek(0, io.SeekEnd)
	_, _ = iWriter.WriteString("\xEF\xBB\xBF") // 写入UTF-8 BOM，防止中文乱码
	csvWriter := csv.NewWriter(iWriter)
	csvWriter.Comma = ','
	csvWriter.UseCRLF = true
	allRows := append(table.ColumnData, table.Data...)
	err = csvWriter.WriteAll(allRows)
	if err != nil {
		return err
	}
	return nil
}

func GetDate() string {
	return time.Now().Format("2006-01-02 15:04:05")
}

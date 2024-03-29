package entry

import "strings"

type Table struct {
	Name string
	Comment string
	// 列信息
	Columns []*Column
	// 数据
	Data [][]string
	// 列数据
	ColumnData [][]string
	/**模板用**/
	BeanPackage string
	TmplPackage string
	Imports map[string]string
	JavaName string
}

func (table *Table)AddRow(aRowData []string)  {
	table.Data = append(table.Data, aRowData)
}

func (table *Table)ToString() string {
	return strings.Join([]string{table.JavaName, table.Comment}, ",")
}

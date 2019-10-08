from datetime import datetime

import const
from jinja2 import Template

const.PACKAGE = 'com.sictiy.jserver.db'
const.PRI = 'PRI'
const.JINJA_DIR = './jinja/'
const.OUT_DIR = './out/'

const.JAVA_TYPE = {
    'int': 'int',
    'bigint': 'long',
    'date': 'Date',
    'varchar': 'String',
    'tinyint': 'short'
}


def get_java_type(sql_type, size):
    java_type = const.JAVA_TYPE.get(sql_type)
    if (java_type == 'short') & (size == '1'):
        java_type = 'boolean'
    return java_type


def get_fields_from_sql_columns(sql_columns):
    fields = []
    for a_column in sql_columns:
        field = TableField(a_column[0], a_column[1], a_column[2], a_column[3])
        fields.append(field)
    return fields


def get_columns_from_fields(fields):
    columns = []
    for field in fields:
        column_temp = [field.name, field.java_type, field.sql_type, field.type_size, field.comment]
        columns.append(column_temp)
    return columns


class TableField:
    def __init__(self, name, sql_type, comment, key):
        self.name = name
        sql_type_str_array = sql_type.split('(')
        self.sql_type = sql_type_str_array[0]
        self.type_size = ''
        if len(sql_type_str_array) > 1:
            self.type_size = sql_type_str_array[1].split(')')[0]
        self.comment = comment
        self.java_type = get_java_type(self.sql_type, self.type_size)
        self.key = key


class Table:
    select = None

    def __init__(self, table, fields):
        # 表名
        self.table = table[0]
        # 表备注
        self.comment = table[1]
        # 表字段
        self.fields = fields
        # 包名
        self.package = const.PACKAGE
        # 类名
        self.java_name = self.get_java_name()
        # 主键
        self.key = self.get_pri_key()

    def set_select(self, select):
        self.select = select
        print(select)

    def get_pri_key(self):
        for filed in self.fields:
            if filed.key == const.PRI:
                return filed.name
        return ''

    def get_java_name(self):
        names = self.table.split('_')
        if len(names) < 2:
            return
        java_name = names[1].capitalize()
        if len(names) > 3:
            for name in names[2:]:
                java_name += name.capitalize()
        return 'J' + java_name

    def generate(self):
        print(self.table)
        self.generate_file('info.java', 'Info.java')
        self.generate_file('mapper.xml', 'Mapper.xml')

    def generate_file(self, temp_file, out_file):
        temp_file = const.JINJA_DIR + temp_file + '.jinja'
        out_file = const.OUT_DIR + self.java_name + out_file
        print(temp_file + "\n" + out_file)
        with open(temp_file) as file_temp:
            template = Template(file_temp.read())
        code = template.render(meta=self)
        with open(out_file, 'w') as file_out:
            file_out.write(code)

    def get_date(self):
        return datetime.today().strftime('%Y-%m-%d')

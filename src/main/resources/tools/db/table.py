from datetime import datetime

import const
from jinja2 import Template
from lxml import etree

# 代码生成包目录
const.PACKAGE = 'com.sictiy.jserver.db'
# 数据库主键
const.PRI = 'PRI'
# jinja模板读取目录
const.JINJA_DIR = './jinja/'
# mybatis 配置目录
const.MYBATIS_CONDIG = '../../mybatis-config.xml'
const.MYBATIS_MAPPERS_DIR = 'mappers/'
# 生成map与代码输出目录
const.MAP_JAVA_DIR = '../../../java/com/sictiy/jserver/db/mapper/'
const.POJO_JAVA_DIR = '../../../java/com/sictiy/jserver/db/pojo/'
const.MAP_CONFIG_DIR = '../../mappers/'

# sqlType 与 javaType 对应字典
const.JAVA_TYPE = {
    'int': 'int',
    'bigint': 'long',
    'date': 'Date',
    'varchar': 'String',
    'tinyint': 'short',
}

# 字段类型 import字典
const.NEED_IMPORT = {
    'Date': 'java.util.Date',
}


# 根据字段列表获取需要添加的import
def get_imports(fields):
    imports = []
    for field in fields:
        import_str = const.NEED_IMPORT.get(field.java_type)
        if import_str is not None:
            imports.append(import_str)
    return imports


# 根据sqlType获取javaType
def get_java_type(sql_type, size):
    java_type = const.JAVA_TYPE.get(sql_type)
    if (java_type == 'short') & (size == '1'):
        java_type = 'boolean'
    return java_type


# 根据数据库字段获取java类属性
def get_fields_from_sql_columns(sql_columns):
    fields = []
    for a_column in sql_columns:
        field = TableField(a_column[0], a_column[1], a_column[2], a_column[3])
        fields.append(field)
    return fields


# 根据java属性获取界面展示的行
def get_columns_from_fields(fields):
    columns = []
    for field in fields:
        column_temp = [field.name, field.java_type, field.sql_type, field.type_size, field.comment]
        columns.append(column_temp)
    return columns


# 首字母大写 其他字母不变(str自带的其他字母会修改为小写）
def capitalize(my_string: str):
    return my_string[:1].upper() + my_string[1:]


# 将新生成的mapper文件添加的mybatis配置中
def insert_mapper_to_mybatis(mapper_name):
    new_mapper = const.MYBATIS_MAPPERS_DIR + mapper_name
    xml = etree.parse(const.MYBATIS_CONDIG, etree.XMLParser(remove_blank_text=True))
    for mappers in xml.xpath('//mappers'):
        for mapper in mappers.getchildren():
            if mapper.get('resource') == new_mapper:
                return
        new_elem = etree.SubElement(mappers, 'mapper')
        new_elem.set('resource', new_mapper)
        break
    xml.write(open(const.MYBATIS_CONDIG, 'wb'), xml_declaration=True, pretty_print=True, encoding='utf-8')


# 数据库一个字段对应类中每一条属性
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

    def up_name(self):
        return capitalize(self.name)


def get_pri_key(fields):
    for filed in fields:
        if filed.key == const.PRI:
            return filed
    return ''


# 一个数据表对应一个java类
class Table:
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
        self.key = get_pri_key(self.fields)
        # 需要额外添加的import
        self.imports = get_imports(self.fields)
        # 选择字段 用于查询
        self.selects = [self.key]

    def set_selects(self, select):
        # select 为 已选的fields.name 列表
        # 默认选主键
        self.selects.clear()
        for field in self.fields:
            if select.count(field.name) > 0 or field == self.key:
                self.selects.append(field)

    def get_java_name(self):
        names = self.table.split('_')
        if len(names) < 2:
            return
        java_name = capitalize(names[1])
        if len(names) >= 3:
            for name in names[2:]:
                java_name += capitalize(name)
        return 'J' + java_name

    def generate(self):
        print('---start generate---')
        self.generate_file('info.java', 'Info.java', const.POJO_JAVA_DIR)
        self.generate_file('mapper.xml', 'Mapper.xml', const.MAP_CONFIG_DIR)
        self.generate_file('mapper.java', 'Mapper.java', const.MAP_JAVA_DIR)
        # 将新mapper 添加到mybatis配置中
        insert_mapper_to_mybatis(self.java_name + 'Mapper.xml')
        print('---end generate---')

    def generate_file(self, temp_file, out_file, out_dir):
        temp_file = const.JINJA_DIR + temp_file + '.jinja'
        out_file = out_dir + self.java_name + out_file
        print(temp_file + "\n" + out_file)
        with open(temp_file) as file_temp:
            template = Template(file_temp.read())
        code = template.render(meta=self)
        with open(out_file, 'w', encoding="utf-8") as file_out:
            file_out.write(code)

    def get_date(self):
        return datetime.today().strftime('%Y-%m-%d')

# -*- coding: utf-8 -*-
# python 3.x
# Filename:const.py
# 定义一个常量类实现常量的功能
# 该类定义了一个方法__setattr()__,和一个异常ConstError, ConstError类继承
# 自类TypeError. 通过调用类自带的字典__dict__, 判断定义的常量是否包含在字典
# 中。如果字典中包含此变量，将抛出异常，否则，给新创建的常量赋值。
# 最后两行代码的作用是把const类注册到sys.modules这个全局字典中。
import sys


class const:
    class ConstError(TypeError): pass

    def __setattr__(self, name, value):
        if name in self.__dict__:
            raise self.ConstError("Can't rebind const (%s)" % name)
        self.__dict__[name] = value


sys.modules[__name__] = const()

# 数据库地址
const.HOST = '127.0.0.1'
# 数据库端口
const.PORT = 3306
# 数据库用户名
const.USER = 'root'
# 密码
const.PASSWORD = '123123'
# 数据库名称
const.DATABASE = 'jserver'
# 字符集
const.CHARSET = 'utf8'

# 代码生成包目录
const.PACKAGE = 'com.sictiy.common.db'
# 点击左侧是否输出数据库字段
const.PRINT_COLUMNS = False
# 数据库主键
const.PRI = 'PRI'
# 数据库索引
const.MUL = 'MUL'
# jinja模板读取目录
const.JINJA_DIR = './jinja/'
# mybatis 配置目录
const.MYBATIS_CONDIG = '../../mybatis-config.xml'
const.MYBATIS_MAPPERS_DIR = 'mappers/'
# 生成代码输出目录
const.PROJECT_DIR = '../../../Common/src/main/java/'
const.MAP_JAVA_DIR = const.PROJECT_DIR + 'com/sictiy/common/db/mapper/'
const.POJO_JAVA_DIR = const.PROJECT_DIR + 'com/sictiy/common/db/pojo/'
# 生成map目录
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

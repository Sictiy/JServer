import pymysql

from tools.db import const


class GetMysqlTableComments:
    db = None
    cursor = None
    connected = False

    def __init__(self, host=const.HOST, user=const.USER, password=const.PASSWORD, database=const.DATABASE, port=const.PORT, charset=const.CHARSET):
        self.connect(host, user, password, database, port, charset)

    def connect(self, host=const.HOST, user=const.USER, password=const.PASSWORD, database=const.DATABASE, port=const.PORT, charset=const.CHARSET):
        if self.connected:
            self.close_db()
        self.db = pymysql.connect(host=host, user=user, password=password, port=port, database=database, charset=charset)
        self.cursor = self.db.cursor()
        self.connected = True
        print('---connect to %s:%s---' % (host, port))

    def get_tables(self, database_name=const.DATABASE):
        # 查询mysql表名和注释
        self.cursor.execute(
            'select table_name,table_comment from information_schema.TABLES where TABLE_SCHEMA=%s order by table_name',
            database_name)
        return self.cursor.fetchall()

    def get_databases(self):
        self.cursor.execute('show databases')
        return self.cursor.fetchall()

    def get_columns(self, table_name):
        # 查询mysql表字段注释
        self.cursor.execute('select column_name,COLUMN_TYPE,column_comment, COLUMN_KEY from information_schema.COLUMNS where TABLE_NAME=%s',
                            table_name)
        return self.cursor.fetchall()

    def close_db(self):
        # 关闭数据库
        if not self.connected:
            return
        self.cursor.close()
        self.db.close()
        self.connected = False
        print('---close db---')


import const
import pymysql

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


class GetMysqlTableComments:
    def __init__(self, host, user, password, database, port, charset):
        self.db = pymysql.connect(host=host, user=user, password=password, port=port, database=database, charset=charset)
        self.cursor = self.db.cursor()
        self.connected = True

    def get_tables(self, database_name):
        # 查询mysql表名和注释
        self.cursor.execute(
            'select table_name,table_comment from information_schema.TABLES where TABLE_SCHEMA=%s order by table_name',
            database_name)
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


if __name__ == '__main__':
    my_database = GetMysqlTableComments(const.HOST, const.USER, const.PASSWORD, const.DATABASE, const.PORT, const.CHARSET)
    tables = my_database.get_tables(const.DATABASE)
    for table in tables:
        print(table)
        column = my_database.get_columns(table[0])
        print(column)
    my_database.close_db()

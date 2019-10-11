from tkinter.tix import Tk, Menu
from tkinter.ttk import *

import const

# from db.const import const
from db.database import GetMysqlTableComments
from db.table import get_fields_from_sql_columns, get_columns_from_fields, Table


def replace_tree_view(tree, values):
    # delete old
    x = tree.get_children()
    for item in x:
        tree.delete(item)
    # insert new
    for value in values:
        tree.insert('', 'end', values=value)


class DbGui:
    def __init__(self):
        # init db and table
        self.db = GetMysqlTableComments(const.HOST, const.USER, const.PASSWORD, const.DATABASE, const.PORT, const.CHARSET)
        self.current_table = None
        # init window
        self.root = Tk()
        self.root.title("dbTools")
        self.root.geometry('960x480')
        self.left = Treeview(self.root)
        self.right = Treeview(self.root)
        self.main_menu = Menu(self.root)
        self.init_left()
        self.init_right()
        self.init_main_menu()
        self.root.config(menu=self.main_menu)
        self.root.bind('Button-3', self.popup_menu)

    def start(self):
        self.set_left(self.db.get_tables(const.DATABASE))
        self.root.mainloop()
        self.close()

    def init_left(self):
        self.left["columns"] = ("1", "2")
        self.left["show"] = "headings"
        self.left.column("1", width=50, anchor='center')
        self.left.column("2", width=50, anchor='center')
        self.left.heading("1", text="table")
        self.left.heading("2", text="comment")
        self.left.insert('', 'end', values=('1', '2'))
        self.left.place(relx=0, rely=0, relwidth=0.2, relheight=1)
        self.left.bind('<ButtonRelease-1>', self.left_click)

    def right_click(self, event):
        if self.current_table is None:
            print('please choose table!')
            return
        selection = []
        for item in self.right.selection():
            item_text = self.right.item(item, "values")
            selection.append(item_text[0])
        self.current_table.set_selects(selection)

    def left_click(self, event):
        for item in self.left.selection():
            item_text = self.left.item(item, "values")
            fields = get_fields_from_sql_columns(self.db.get_columns(item_text[0]))
            self.current_table = Table(item_text, fields)
            self.set_right(get_columns_from_fields(fields))
            break

    def init_right(self):
        self.right["columns"] = ("1", "2", "3", "4", "5")
        self.right["show"] = "headings"
        self.right.column("1", width=50, anchor='center')
        self.right.column("2", width=50, anchor='center')
        self.right.column("3", width=50, anchor='center')
        self.right.column("4", width=50, anchor='center')
        self.right.column("5", width=50, anchor='center')
        self.right.heading("1", text="column")
        self.right.heading("2", text="java type")
        self.right.heading("3", text="sql type")
        self.right.heading("4", text="max size")
        self.right.heading("5", text="comment")
        self.right.place(relx=0.2, rely=0, relwidth=0.8, relheight=1)
        self.right.bind('<ButtonRelease-1>', self.right_click)

    def set_left(self, values):
        replace_tree_view(self.left, values)

    def set_right(self, values):
        replace_tree_view(self.right, values)

    def init_main_menu(self):
        # 被注释代码为在菜单嵌入子菜单
        # menu_run = Menu(self.main_menu)
        # self.main_menu.add_cascade(label="运行", menu=menu_run)
        self.main_menu.add_command(label="生成", command=self.generate)
        # menu_run.add_separator()
        self.main_menu.add_command(label="退出", command=self.root.destroy)

    def close(self):
        if self.db.connected:
            self.db.close_db()
        print('---closed---')

    def generate(self):
        if self.current_table is None:
            print('please choose table!')
            return
        print('---start generate---')
        self.current_table.generate()
        print('---end generate---')

    def popup_menu(self, event):
        self.main_menu.post(event.x_root, event.y_root)


if __name__ == '__main__':
    db_gui = DbGui()
    db_gui.start()

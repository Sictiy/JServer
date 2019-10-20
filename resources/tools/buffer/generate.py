"""
@Author: sictiy xu
@Date: 2019-09-26 10:34:04
@LastEditors:
@LastEditTime: 2019-09-26 10:36:20
@Description: file content
"""

import fnmatch
import os


def find_files(path, f):
    for root, dirs, files, in os.walk(path):
        for fileName in fnmatch.filter(files, f):
            yield os.path.join(root, fileName)


def main():
    cmd_dir = os.path.split(os.path.realpath(__file__))[0]
    src_dir = os.path.abspath('../../buffer')
    dst_dir = os.path.abspath('../../../java')
    for fileName in find_files(src_dir, "*.fbs"):
        cmd = cmd_dir + "\\flatc.exe -j -o " + dst_dir + " " + fileName
        print(cmd)
        os.system(cmd)
    print("over")


if __name__ == '__main__':
    main()

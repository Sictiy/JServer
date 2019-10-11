import os
import sys

base_dir = os.path.dirname(os.path.abspath(__file__)) + '/../'
sys.path.append(base_dir)
from db.gui import DbGui

if __name__ == '__main__':
    gui = DbGui()
    gui.start()

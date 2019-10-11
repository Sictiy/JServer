'''
@Author: sictiy xu
@Date: 2019-10-11 15:21:20
@LastEditors: 
@LastEditTime: 2019-10-11 18:08:24
@Description: file content
'''
import os

# os.system("rmdir /s/q target")
# os.system("mkdir target")

source="src/main/java/com/sictiy/jserver/entry/single"
# cmd1="javac -encoding UTF-8 -cp $JAVA_HOME/lib/tools.java "+source+"/SingleInstance.java "+source+"/SingleProcessor.java -d target"
cmd2="javac -encoding UTF-8 -cp target -processor com.sictiy.jserver.entry.single.SingleProcessor "+source+"/TestInstanceTest.java -d target"

# print('---')
# os.system(cmd1)
print('---')
os.system(cmd2)
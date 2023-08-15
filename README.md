# NOTE
This repository will not accept any commits until Jul 2025. 该存储库在2025年7月之前不会接受任何提交。
# HDQS
A text game framework that providing some basic features. Such as IO, text manage and mod support. 提供一些基本功能的文本游戏框架。如IO、文本管理和mod支持。
# Warnning
The following content will no longer be written in both Chinese and English. Please use a web translator to cotinue read.
# 编译
在此项目中，Test与程序本体没有分离。
编译方法：请先安装java17，注意，不是java1.7。
仅提供Windows端的编译方法
* 下载release中的源码
* 新建一个文本文档，修改为.bat后缀
* 使用文本编辑器打开Build.bat，复制其中内容
* 粘贴至刚才新建的bat文件中，这一步的目的是把Unix(LF)转换为Windows(CLRF)，才能在cmd中运行
* 保存新建的bat文件，双击Run
如果你有不同的想法，这里提供了HDQS/Build.bat的内容
```
    @echo off
    echo.

    mkdir out
    cd out
    mkdir production
    cd production
    mkdir HDQS
    cd HDQS
    mkdir testRes
    cd testRes
    xcopy ..\..\..\..\testRes /s/e/y
    cd ..
    cd ..
    cd ..
    cd ..

    md out\production\HDQS
    javac -classpath "src;out\production\HDQS\testRes\io\GetCode[77a7c91a].jar" -d ..\out\production\HDQS -encoding UTF-8 src\Launcher.java

    pause
```
# 运行

@echo off
chcp 65001
echo.
echo 编译中

md out\production\HDQS
cd src
chcp 936
javac -d ..\out\production\HDQS -encoding UTF-8 Launcher.java
chcp 65001
echo 编译完成
cd ..

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


echo.
echo 资源文件拷贝完成
echo 正在准备所有测试
cd out
cd production
cd HDQS

echo 正在运行所有测试
echo.

java Launcher -t all

echo.
pause
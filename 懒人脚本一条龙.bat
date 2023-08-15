@echo off
chcp 65001
echo.
echo 编译中

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
chcp 936
javac -classpath "src;out\production\HDQS\testRes\io\GetCode[77a7c91a].jar" -d ..\out\production\HDQS -encoding UTF-8 src\Launcher.java
echo 编译完成
chcp 65001
echo.

java -classpath "out\production\HDQS;out\production\HDQS\testRes\io\GetCode[77a7c91a].jar" Launcher -t all

echo.
pause
@echo off

chcp 65001
echo 本窗口编码为 UTF - 8
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


echo.
echo 资源文件拷贝完成
echo.
echo.
pause


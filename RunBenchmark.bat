@echo off
echo.

chcp 65001
echo 本窗口编码为 UTF - 8
echo.

echo 正在准备所有基准测试
cd out
cd production
cd HDQS

echo 正在运行所有基准测试
echo.

java Launcher -benchmark all

echo.
pause
@echo off
echo.
echo ������

md out\production\HDQS
cd src
javac -d ..\out\production\HDQS -encoding UTF-8 Launcher.java

echo �������
echo.
pause
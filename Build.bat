echo.
echo ±‡“Î÷–

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
echo ±‡“ÎÕÍ≥…

pause
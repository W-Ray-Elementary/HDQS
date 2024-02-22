cd ..
rmdir /S /Q "out\batchBuild"

:: HDQS-Frame.jar
javac -classpath "src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "H:\VCS\HDQS\src\main\java\com\plzEnterCompanyName\HDQS\text\frame\FrameExample.java"
Xcopy "META-INF\HDQS" "out\batchBuild\temp\META-INF\" /s/e/y
md "out\batchBuild\artifacts"
jar -cvfm out\batchBuild\artifacts\HDQS-Frame-0.1.jar out\batchBuild\temp\META-INF\MANIFEST.MF -C out\batchBuild\temp .
rmdir /S /Q "out\batchBuild\temp"
cd script
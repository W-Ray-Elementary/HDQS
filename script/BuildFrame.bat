cd ..
rmdir /S /Q "out\batchBuild"

:: HDQS-Frame.jar
javac -classpath "src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\text\frame\FrameExample.java"
Xcopy "META-INF\Frame" "out\batchBuild\temp\META-INF\" /s/e/y
md "out\batchBuild\artifacts"
jar -cvfm out\batchBuild\artifacts\HDQS-Frame.jar out\batchBuild\temp\META-INF\MANIFEST.MF -C out\batchBuild\temp .
rmdir /S /Q "out\batchBuild\temp"

:: HDQS-Frame-source.jar
Xcopy "src\main\java" "out\batchBuild\src\" /s/e/y
Xcopy "META-INF\Frame-source" "out\batchBuild\src\META-INF\" /s/e/y
jar -cvfm out\batchBuild\artifacts\HDQS-Frame-source.jar out\batchBuild\src\META-INF\MANIFEST.MF -C out\batchBuild\src .
rmdir /S /Q "out\batchBuild\src"

cd script
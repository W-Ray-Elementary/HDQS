cd ..
rmdir /S /Q "out\batchBuild"
javac -classpath "src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\Launcher.java"
Xcopy "META-INF" "out\batchBuild\temp\META-INF\" /s/e/y
md "out\batchBuild\HDQS\bin"
jar -cvfm out\batchBuild\HDQS\bin\HDQS.jar out\batchBuild\temp\META-INF\MANIFEST.MF -C out\batchBuild\temp .
rmdir /S /Q "out\batchBuild\temp"
copy "src\main\batch\run.bat" "out\batchBuild\HDQS\" /y
xcopy "src\main\resourses\*" "out\batchBuild\HDQS" /s /e /y
xcopy "src\test\resources\*" "out\batchBuild\HDQS" /s /e /y
cd script
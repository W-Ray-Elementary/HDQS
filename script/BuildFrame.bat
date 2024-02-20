cd ..
rmdir /S /Q "out\batchBuild"
Xcopy "lib" "out\batchBuild\HDQS\lib\" /s/e/y

:: google-UnicodeReader-2.2.1-alpha.jar
javac -classpath "src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\google\UnicodeReader\java\io\base\UnicodeReader.java"
Xcopy "META-INF\google-UnicodeReader-2.2.1-alpha" "out\batchBuild\temp\META-INF\" /s/e/y
md "out\batchBuild\HDQS\lib"
jar -cvfm out\batchBuild\HDQS\lib\google-UnicodeReader-2.2.1-alpha.jar out\batchBuild\temp\META-INF\MANIFEST.MF -C out\batchBuild\temp .
rmdir /S /Q "out\batchBuild\temp"

:: HDQS.jar
javac -classpath "out\batchBuild\HDQS\lib\google-UnicodeReader-2.2.1-alpha.jar;src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\Launcher.java"
javac -classpath "src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\util\args\HelloWorld.java"
javac -classpath "src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\util\args\FrameDemo.java"
Xcopy "META-INF\HDQS" "out\batchBuild\temp\META-INF\" /s/e/y
md "out\batchBuild\HDQS\bin"
md "out\batchBuild\HDQS\settings"
jar -cvfm out\batchBuild\HDQS\bin\HDQS.jar out\batchBuild\temp\META-INF\MANIFEST.MF -C out\batchBuild\temp .
rmdir /S /Q "out\batchBuild\temp"

copy "src\main\batch\FrameDemo.exe" "out\batchBuild\HDQS\" /y
xcopy "src\main\resourses\*" "out\batchBuild\HDQS" /s /e /y
xcopy "src\test\resources\*" "out\batchBuild\HDQS" /s /e /y
cd script
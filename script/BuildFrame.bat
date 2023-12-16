cd ..
rmdir /S /Q "out\batchBuild"

:: UnicodeReader.jar
javac -classpath "src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\google\UnicodeReader\java\io\base\UnicodeReader.java"
Xcopy "META-INF\UnicodeReader" "out\batchBuild\temp\META-INF\" /s/e/y
md "out\batchBuild\HDQS\lib"
jar -cvfm out\batchBuild\HDQS\lib\UnicodeReader.jar out\batchBuild\temp\META-INF\MANIFEST.MF -C out\batchBuild\temp .
rmdir /S /Q "out\batchBuild\temp"

:: HDQS-FileIO.jar
javac -classpath "out\batchBuild\HDQS\lib\UnicodeReader.jar;src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\io\PATH.java"
javac -classpath "out\batchBuild\HDQS\lib\UnicodeReader.jar;src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\io\FileAndString.java"
Xcopy "META-INF\HDQS-FileIO" "out\batchBuild\temp\META-INF\" /s/e/y
jar -cvfm out\batchBuild\HDQS\lib\HDQS-FileIO.jar out\batchBuild\temp\META-INF\MANIFEST.MF -C out\batchBuild\temp .
rmdir /S /Q "out\batchBuild\temp"

:: Lexicon.jar
javac -classpath "out\batchBuild\HDQS\lib\HDQS-FileIO.jar;src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\util\Lexicon.java"
javac -classpath "out\batchBuild\HDQS\lib\HDQS-FileIO.jar;src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\util\Lexicons.java"
javac -classpath "out\batchBuild\HDQS\lib\HDQS-FileIO.jar;src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\io\ConfigureFile.java"
Xcopy "META-INF\Lexicon" "out\batchBuild\temp\META-INF\" /s/e/y
jar -cvfm out\batchBuild\HDQS\lib\Lexicon.jar out\batchBuild\temp\META-INF\MANIFEST.MF -C out\batchBuild\temp .
rmdir /S /Q "out\batchBuild\temp"

:: SmartIO.jar
javac -classpath "out\batchBuild\HDQS\lib\HDQS-FileIO.jar;out\batchBuild\HDQS\lib\Lexicon.jar;src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\io\smartIO2\Message.java"
javac -classpath "out\batchBuild\HDQS\lib\HDQS-FileIO.jar;out\batchBuild\HDQS\lib\Lexicon.jar;src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\io\smartIO2\MessageManager.java"
Xcopy "META-INF\SmartIO" "out\batchBuild\temp\META-INF\" /s/e/y
jar -cvfm out\batchBuild\HDQS\lib\SmartIO.jar out\batchBuild\temp\META-INF\MANIFEST.MF -C out\batchBuild\temp .
rmdir /S /Q "out\batchBuild\temp"

:: Frame.jar
javac -classpath "out\batchBuild\HDQS\lib\HDQS-FileIO.jar;out\batchBuild\HDQS\lib\SmartIO.jar;out\batchBuild\HDQS\lib\Lexicon.jar;src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\text\frame\Frame.java"
Xcopy "META-INF\Frame" "out\batchBuild\temp\META-INF\" /s/e/y
jar -cvfm out\batchBuild\HDQS\lib\Frame.jar out\batchBuild\temp\META-INF\MANIFEST.MF -C out\batchBuild\temp .
rmdir /S /Q "out\batchBuild\temp"

:: HDQS.jar
javac -classpath "out\batchBuild\HDQS\lib\HDQS-FileIO.jar;out\batchBuild\HDQS\lib\Lexicon.jar;out\batchBuild\HDQS\lib\SmartIO.jar;out\batchBuild\HDQS\lib\Frame.jar;src\main\java" -d "out\batchBuild\temp" -encoding UTF-8 "src\main\java\com\plzEnterCompanyName\HDQS\Launcher.java"
Xcopy "META-INF\HDQS" "out\batchBuild\temp\META-INF\" /s/e/y
md "out\batchBuild\HDQS\bin"
md "out\batchBuild\HDQS\settings"
jar -cvfm out\batchBuild\HDQS\bin\HDQS.jar out\batchBuild\temp\META-INF\MANIFEST.MF -C out\batchBuild\temp .
rmdir /S /Q "out\batchBuild\temp"

copy "src\main\batch\FrameDemo.exe" "out\batchBuild\HDQS\" /y
cd script
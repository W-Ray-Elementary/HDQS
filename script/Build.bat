cd ..
javac -classpath "src\main\java" -d "out\batchBuild\HDQS\bin" -encoding UTF-8 src\main\java\com\plzEnterCompanyName\HDQS\Launcher.java
Xcopy "META-INF" "out\batchBuild\HDQS\bin\META-INF\" /s/e/y
jar -cvfm out\batchBuild\HDQS\bin\HDQS.jar "out\batchBuild\HDQS\bin\META-INF\MANIFEST.MF" -C out\batchBuild\HDQS\bin .
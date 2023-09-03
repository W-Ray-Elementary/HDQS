# NOTE
This repository will not accept any commits until Jul 2025. 该存储库在2025年7月之前不会接受任何提交。
# HDQS
A text game framework that providing some basic features. Such as IO, text manage and mod support. 提供一些基本功能的文本游戏框架。如IO、文本管理和mod支持。
# Warnning
The following content will no longer be written in both Chinese and English. Please use a web translator to read it.
# 编译
请先安装java17，注意，不是java1.7。
仅提供Windows端的编译方法
* 下载release中的源码
* 进入`script`目录
* 双击`Build.bat`
如果你有不同的想法，这里提供了[Build.bat](script/Build.bat)的内容
```
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
pause
```
# 运行

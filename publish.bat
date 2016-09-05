copy /Y D:\Users\monibm\git\forms-library\target\formslibrary-1.0.0-SNAPSHOT.jar c:\robot-tests\resources\lib\formslibrary.jar
python -m robot.libdoc FormsLibrary C:\robot-tests\resources\lib\formslibrary.xml
python -m robot.libdoc FormsLibrary C:\robot-tests\documentation\formslibrary.html

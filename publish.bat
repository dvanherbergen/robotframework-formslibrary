copy /Y C:\git\forms-library\target\formslibrary-0.0.1-SNAPSHOT.jar c:\robot-tests\resources\lib\formslibrary.jar
python -m robot.libdoc FormsLibrary C:\robot-tests\resources\lib\formslibrary.xml
python -m robot.libdoc FormsLibrary C:\robot-tests\documentation\formslibrary.html

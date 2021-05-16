set projectLocation=C:\Users\senheng\Documents\repo\syok-java-appium
 
cd %projectLocation%
 
set classpath=%projectLocation%\bin;%projectLocation%\lib\*;%projectLocation%\bin\*
 
java -cp %classpath% org.testng.TestNG %projectLocation%\testng.xml
 
pause
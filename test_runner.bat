set classpath=.\bin;.\lib\*;.\bin\*
 
java -cp %classpath% org.testng.TestNG .\testng.xml
 
pause
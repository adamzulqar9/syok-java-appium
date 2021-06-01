# syok-java-appium
Mobile app testing using Java and Appium with TestNG as the test framework

### Test Scenarios:
1. Testing the radio functionalities
2. Testing the video functionalities

---------------------------------------------------------

### Project structure:
1. Project built using maven (more info can be obtained in [pom.xml](https://github.com/adamzulqar9/syok-java-appium/blob/alpha-dev/pom.xml))
2. 3 main libraries/APIs used
* io.appium 7.0.0
* org.seleniumhq.selenium 3.141.59
* org.testng 7.4.0
3. Environment: JRE 1.8.0_261

---------------------------------------------------------

#### Project is separated into 3 main subfolders:

![alt text](https://github.com/adamzulqar9/syok-java-appium/blob/alpha-dev/project_structure.PNG "Project Structure")
1. **functions**
* Helper functions for tests are defined here, will then be called from the test scripts
* Reason: Code reusability, easier code maintenance, changes to API can be handled easier (less risk to break entire tests)
2. **test**
* BaseTest is the base class for all the tests, this is where setup and teardown methods are called
* Test classes will extend BaseTest class and perform specific actions for testing specific features/functionalities
* Reason: Code maintenance, test extensibility (create new class and extend BaseTest class for new tests)
3. **variables**
* All variables and elements that will be used in testing will be declared here
* Reason: Variables reusability, changes to elements can be handled easier (less risk to break entire tests)

---------------------------------------------------------

### Test execution:
1. Execute test using TestNG config from IntelliJ
2. Execute test using test_runner.bat (using testng.xml) from Jenkins with the option of scheduler (e.g. every 5 minute)

---------------------------------------------------------

### Test Execution Demo:
[![Watch the video](https://i.imgur.com/S1ChGPe.png)](https://streamable.com/hfiqdk)

// TODO:
1. Implement checkings for video: Appium Inspector/UIAutomatorViewer doesnt return webview/video elements. May need to implement screenshot verification to verify video
2. Implement proper logging for debugging

# TODO MVC feature

 ## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Prerequisites](#prerequisites)
* [Contents](#contents)
* [Steps](#steps)

## General info
This project is written to test the ToDo MVC feature. 
	
## Technologies
Project is created with:
* java: Version "1.8.0_321"
* cucumber-java : Version 7.2.3
* cucumber-junit : Version 7.2.3
* selenium-java : Version 4.1.1
* junit : Version 4.13.1
	
## Prerequisites
System installed with
* Java Version "1.8.0_321"
* Eclipse Version: 2021-12 (4.22.0)
* Install the Maven plugin in eclipse and add the following dependencies in pom.xml
```
<dependencies>
<!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-java -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.2.3</version>
</dependency>

<!-- https://mvnrepository.com/artifact/junit/junit -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.1</version>
    <scope>test</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-junit -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>7.2.3</version>
    <scope>test</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java -->
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.1.1</version>
</dependency>

</dependencies>
```
## Contents

Cucumber feature file with the Gherkins Given-When-Then scenarios : src/test/resources/Features/todo.feature

Java Steps definition or Implementation : src\test\java\StepDefinitions\todoSteps.java

Junit Test runner file : src\test\java\StepDefinitions\TestRunner.java

Web Drivers for chrome and firefox : src\test\resources\Drivers\chromedriver.exe

                                   : src\test\resources\Drivers\geckodriver.exe
                                   
Reports after test run generated in : target\cucumberReports\report.json

                                      target\JunitReports\report.xml
                                      
                                      target\HtmlReports\report.html
                                      
## Steps

1. Select the browser in src\test\java\StepDefinitions\todoSteps.java as "chrome" or "firefox" as
```
String webdriver = "chrome";
```
or
```
String webdriver = "firefox";
```
2. Run file src\test\java\StepDefinitions\TestRunner.java
3. The reports are generated in 3 formats in the 'target' folder - html, json and xml along with screenshots for failed scenarios.







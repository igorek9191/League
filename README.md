### About test automation task
Need to test open api ```https://api.thecatapi.com/v1/``` <br />
Documentation https://docs.thecatapi.com/ <br />
Test design was done according to the assigned task, the text of which I lost :) <br />
There are 2 test classes which go parallel

### Required Tools
Java 14<br />
Gradle 7.0<br />
allure 2.14.0

### Launch of test
In the root of project put in command prompt:<br />
```gradle clean test --project-prop api_key=852edcf0-7ad3-4c42-b3b1-feab715e5fd5```<br />
OR <br />
```./gradlew clean test --project-prop api_key=852edcf0-7ad3-4c42-b3b1-feab715e5fd5``` (if you work at Mac)

### Test report
In the root of project put in command prompt:<br />
```allure serve ./build/allure-results/```  

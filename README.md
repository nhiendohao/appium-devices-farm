# selenium-junit5-project-template

## This framework consists of:
- Selenium – 3.141.59
- Java 11
- JUnit – 4.13.2
- Maven – 3.8.1
- Allure Report – 2.14.0
- Allure JUnit4 – 2.14.0

## Maven command
**Run all tests**
```
mvn clean test
```
**Run test by tags**
```
mvn clean verify -Dgroups="GoogleHomeSearch"
```

### Allure Report
**Install Allure Report**
```
brew install allure
```

**Generate Allure Report**
```
allure serve allure-results
```


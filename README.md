
## This framework consists of:
- Selenium 4.9.1
- Java 11
- JUnit 5
- Maven 3.8.1
- Allure Report 2.14.0
- Allure JUnit4 2.14.0

## Maven command
- Run all tests
```
mvn clean test
```

**Run test by tags**

- Run tests which tagged with `load` and exclude tagged with `api`
```
mvn test -Dgroups="load" -DexcludedGroups="api"
```

- Run tests which tagged with `api` or  `performance`
```
mvn test -Dgroups="api | performance"
```

- Run tests which tagged both `api` and  `performance`
```
mvn test -Dgroups="api & performance"
```

### Allure Report
- Install Allure Report
```
brew install allure
```

- Generate Allure Report
```
allure serve allure-results
```


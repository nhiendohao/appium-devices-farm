
## This framework consists of:
- Selenium 4.9.1
- Java 11
- JUnit 5
- Maven 3.8.1
- Allure Report 2.14.0
- Allure JUnit4 2.14.0

## Maven command
**Run all tests**
```
    mvn clean test
```

**Run test by tags**
- Run tests which tagged with `YoutubeSearch` and exclude tagged with `api`

```
    mvn clean test -Dgroups="YoutubeSearch" -DexcludedGroups="api"
```

- Run tests which tagged with `api` or  `YoutubeSearch`
```
    mvn test -Dgroups="api | YoutubeSearch"
```

- Run tests which tagged both `api` and  `YoutubeSearch`
```
    mvn test -Dgroups="api & YoutubeSearch"
```

### Allure Report
- Install Allure Report
```
    brew install allure
```

- Open Allure Report
```
allure serve [path/to/allure-results]
Example:
    allure serve target/allure-results
```

- Generate clean report folder
```
allure generate [path/to/allure-results] --clean --output [path/to/allure-report]
Example:
    allure generate target/allure-results --clean --output allure-report
```
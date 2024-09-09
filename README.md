
---

# Backend - API REST - FIAP Restaurant

## Running Tests

- To run unit tests:

```sh
mvn test
```

- To run integration tests:

```sh
mvn test -P integration-test
```

- To run system tests:

```sh
mvn test -P system-test
```

- To run Performance Test - with Gatling:

```sh
mvn gatling:test -P performance-test
```

## Running Reports


- To run Cucumber Report:

```sh
start ./target/cucumber-reports/cucumber.html
```

- To run Allure Report:

```sh
 allure serve target/allure-results    
```

(Note: It is necessary to have Allure installed
<br>
npm install -g allure <br>
npm install -g allure-commandline)

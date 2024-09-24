
# FIAP Tech Challenge - Restaurant Reservation and Review API

## Description

This project is part of FIAP's Tech Challenge and consists of a restaurant reservation and review system API. The goal is to allow users to make reservations at restaurants, review their experiences, and for restaurants to manage their reservations.

This application is built using **Spring Boot** and **Java**, and it provides RESTful endpoints for managing **restaurants**, **reservations**, and **reviews**.

## Features

1. **Restaurant Registration**:
    - Restaurants can register on the system, providing information such as name, location, type of cuisine, opening hours, and seating capacity.
    - Endpoints:
        - `POST /restaurant` - Create a new restaurant.
        - `GET /restaurant` - Search for restaurants by name, location, or type of cuisine.
        - `GET /restaurant/{restaurantId}` - Find a restaurant by ID.

2. **Customer Registration**  
   Customers can be managed in the system, including creation and retrieval of information.  
   **Endpoints**:
   - `POST /customer` - Creates a new customer and returns the details of the created customer.
   - `GET /customer` - Returns a list of all customers.
   - `GET /customer/{id}` - Returns the details of a specific customer by ID.
   - `DELETE /customer/{id}` - Deletes a customer by ID.


3. **Table Reservations**:
    - Users can make reservations at restaurants for specific dates and times.
    - Endpoints:
        - `POST /reservation` - Create a new reservation.
        - `GET /reservation/restaurant/{restaurantId}/opened` - Find open reservations for a restaurant within a specified time range.
        - `PUT /reservation/{reservationId}` - Update the status or details of a reservation.


4. **Reviews and Comments**:
    - After visiting, users can rate the restaurant and leave comments about their experience.
    - Endpoints:
        - `POST /review` - Create a new review.
        - `GET /review/restaurant/{restaurantId}` - Find all reviews for a restaurant.
        - `GET /review/customer/{customerId}` - Find all reviews made by a customer.
        - `GET /review/{reviewId}` - Find a specific review by its ID.
        - `DELETE /review/{id}` - Deletes a specific review by ID.

5. **Restaurant Search**:
    - Users can search for restaurants by name, location, or type of cuisine.
    - Endpoint:
        - `GET /restaurant?name={name}&location={location}&kitchenType={kitchenType}` - Advanced restaurant search.

6. **Reservation Management**:
    - Restaurants can manage reservations by viewing and updating the status of their tables.
    - Endpoints:
        - `GET /reservation/customer/{customerId}/completed` - View completed reservations for a customer.
        - `PUT /reservation/{reservationId}` - Update the reservation status (reserved, waiting, in progress, completed, or canceled).


## Technologies Used
- **Java 17**: Main programming language.
- **Spring Boot 3.3.3**: Framework for creating APIs and microservices.
- **Spring Data JPA**: Abstraction for data persistence.
- **PostgreSQL**: Relational database.
- **Flyway**: Version control for database migrations.
- **JUnit & Mockito**: Unit testing frameworks.
- **Rest-Assured**: Framework for API testing.
- **Swagger**: Interactive API documentation.
- **Gatling**: Tool for performance testing.
  Here is the updated section with **Cucumber** and **Allure** added:

## Technologies Used
- **Java 17**: Main programming language.
- **Spring Boot 3.3.3**: Framework for creating APIs and microservices.
- **Spring Data JPA**: Abstraction for data persistence.
- **PostgreSQL**: Relational database.
- **Flyway**: Version control for database migrations.
- **JUnit & Mockito**: Unit testing frameworks.
- **Rest-Assured**: Framework for API testing.
- **Swagger**: Interactive API documentation.
- **Allure**: Framework for generating detailed and visually appealing test reports.
- **Gatling**: Tool for performance testing.
- **Cucumber**: Behavior-Driven Development (BDD) testing tool to ensure functionality aligns with business requirements.

### API Documentation
The API documentation can be accessed after starting the application at:
```
http://localhost:8080/swagger-ui.html
```

### Prerequisites:
- Java 17+
- Maven 3.6+


## Main Dependencies

- **Spring Boot Starter Web**: For creating REST APIs.
- **Spring Boot Starter Data JPA**: For managing data with JPA.
- **Flyway**: For database migrations control.
- **PostgreSQL**: Database used in production environments..
- **Lombok**: To reduce boilerplate code.
- **SpringDoc OpenAPI**: For API documentation using Swagger.
- **JUnit, Rest-Assured, Cucumber, Allure**: Testing tools.

The complete list of dependencies can be found in the `pom.xml` file.

## How to Run the Application


### Steps to run:

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/your-repository.git
   ```

2. Navigate to the project directory:
   ```bash
   cd your-repository
   ```

3. Install the dependencies:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`.


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

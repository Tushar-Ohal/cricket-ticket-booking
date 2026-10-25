# Cricket Ticket Booking System

A backend application built using Java and Spring Boot for booking cricket match tickets. The project handles match details, seat availability, bookings, and cancellations.

I built this project to practise backend development and understand how to handle concurrent booking requests when multiple users try to book the same seat.

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA and Hibernate
- MySQL
- Maven
- REST APIs
- JUnit and Spring Boot Test

## Features

- Create and retrieve cricket match details.
- View available seats for a match.
- Book seats for a cricket match.
- Cancel existing bookings.
- Validate booking requests and handle invalid input.
- Handle cases where a seat is already booked.
- Use pagination and sorting where supported by the API.
- Handle concurrent booking requests to prevent the same seat from being booked twice.
- Return appropriate error responses for different failure cases.

## Concurrency Handling

One of the main parts of this project is handling concurrent booking requests.

If two requests try to book the same seat at nearly the same time, the application needs to prevent both requests from successfully booking it.

The booking logic uses a conditional database update to check seat availability before changing its status. This helps prevent duplicate bookings when multiple requests compete for the same seat.

The project also includes a concurrency test to check this behaviour.

## Project Structure

```text
src/main/java/com/tushar/cricketbooking/
├── controller/
├── dto/
├── entity/
├── enums/
├── exception/
├── repository/
├── service/
└── CricketBookingApplication.java
```

- **controller** – Handles incoming REST API requests.
- **dto** – Defines request and response objects.
- **entity** – Contains JPA entities mapped to database tables.
- **enums** – Defines booking, match, and seat statuses.
- **exception** – Handles application exceptions and error responses.
- **repository** – Handles database operations.
- **service** – Contains the business logic.

## Database Setup

This project uses MySQL.

1. Install MySQL and start the MySQL server.
2. Create the database:

```sql
CREATE DATABASE cricket_booking;
```

3. Configure the database credentials using environment variables.

The application reads the following settings from `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cricket_booking
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD}
```

Set these environment variables before running the application:

```text
DB_USERNAME=root
DB_PASSWORD=your_mysql_password
```

Replace `your_mysql_password` with your local MySQL password. Do not commit your actual database password to GitHub.

## How to Run

### Prerequisites

- Java 17
- MySQL
- Maven, or the Maven Wrapper included in the project

### Steps

1. Clone the repository:

```bash
git clone https://github.com/Tushar-Ohal/cricket-ticket-booking.git
```

2. Open the project in Eclipse or your preferred Java IDE.

3. Create the `cricket_booking` database and configure the database environment variables.

4. Run the application using the main class:

```text
CricketBookingApplication.java
```

Alternatively, from the project directory, run:

```bash
mvn spring-boot:run
```

If Maven is not installed, use the Maven Wrapper included in the project:

On Windows:

```cmd
mvnw.cmd spring-boot:run
```

The application starts on port `8080` by default.

## API Endpoints

The application provides REST APIs for match management, seat availability, and bookings.

| Operation | Endpoint |
|---|---|
| Match management | `/api/matches` |
| Get seats for a match | `/api/matches/{matchId}/seats` |
| Book seats for a match | `/api/bookings/matches/{matchId}` |

The exact HTTP methods and request bodies depend on the controller implementation.

## Testing

The project includes Spring Boot tests and a booking concurrency test.

The tests help verify application behaviour and the handling of competing booking requests.

Run the test suite using:

```bash
mvn test
```

Or, on Windows:

```cmd
mvnw.cmd test
```

## Future Improvements

- Add authentication and authorization.
- Integrate a real payment gateway.
- Deploy the application to a cloud platform.
- Improve API documentation with Swagger/OpenAPI.

## Author

**Tushar Ohal**

[GitHub Profile](https://github.com/Tushar-Ohal)
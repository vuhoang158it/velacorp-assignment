# This project implements a RESTful API for managing products and orders.

## Requirements

- Java 21
- Maven 3.6.3 or higher
- Docker
- Docker Compose

## Technologies Used

- Spring Boot 3.3.1
- Spring Data JPA
- PostgreSQL 16
- Log4j2

## Docker Compose Setup

### Start Docker Compose:

Make sure Docker and Docker Compose are installed on your machine. Then, run the following command to start PostgreSQL
using Docker Compose: `docker-compose up -d`

## Running the Application

- Configure the application to run using `dev` profile (`application-dev.yml`)
- Selecting `dev` here will enable configuration from the `application-dev.yml` file. The "dev" environment is used to
  ensure that the DataLoader class performs sample data migration when launching the application.
- Run the application using the following command: `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
- Sample data will be available in the database after the application starts.
- The application will be accessible at `http://localhost:8888`
- API documentation will be available at `http://localhost:8888/api/swagger-ui/index.html`

# Content Upload Service

## Description

The **Content Upload Service** is a microservice developed in Java using the Spring Boot framework. It enables content uploads and carousel processing.

## Project Structure

The main structure of the project is as follows:

```
content-upload-service/
├── src/               # Project source code
├── pom.xml            # Maven configuration
├── Dockerfile         # Docker file for containerization
├── .gitignore         # Version control file
├── mvnw               # Maven wrapper
├── mvnw.cmd           # Maven wrapper for Windows
└── README.md          # Project documentation
```

## Prerequisites

Ensure you have the following software installed:

- Java 17+
- Maven
- Docker (optional, for containerization)

## How to Run the Project Locally

### 1. Compile the Project

In the root directory of the project, run the following command:

```bash
mvn clean package
```

### 2. Run the Project

After compilation, start the service:

```bash
java -jar target/content-upload-service-0.0.1-SNAPSHOT.jar
```

The application will be available at: `http://localhost:8080`

---

## How to Run Using Docker

### 1. Build the Docker Image

Run the following command to build the image:

```bash
docker build -t content-upload-service .
```

### 2. Run the Container

Run the container with the command:

```bash
docker run -p 8080:8080 content-upload-service
```

The service will be accessible at: `http://localhost:8080`

---

## Additional Configurations

### Default Port

By default, the service uses port **8080**. To change the port, edit the `application.properties` file:

```properties
server.port=8081
```

### Docker Network

If you need a custom network, create one using the command:

```bash
docker network create my-custom-network
```

Then run the container in this network:

```bash
docker run --network my-custom-network -p 8080:8080 content-upload-service
```

---

## Technologies Used

- Java 17
- Spring Boot 3.4.0
- Maven
- Docker

## Contribution

Feel free to contribute to the project! To do so:

1. Fork the repository.
2. Create a branch for your feature (`git checkout -b feature/new-feature`).
3. Commit your changes (`git commit -m 'Added new feature'`).
4. Push to the branch (`git push origin feature/new-feature`).
5. Open a Pull Request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more information.


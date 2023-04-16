# Capital Gains Tax Calculator

## Stack

* [Kotlin](https://kotlinlang.org/)

## Prerequisites

* **[Required]** Java JDK 8+
* **[Required]** [Docker](https://www.docker.com/): To run local Postgres and optionally the application itself
* **[Optional]** [IntelliJ IDEA](https://www.jetbrains.com/idea/): To develop with Kotlin

## Building and running

### Gradle

Gradle can be used to run some commands:

* `./gradlew dependencies`: Install dependencies
* `./gradlew build`: Builds the project. It also installs the dependencies.
* `./gradlew test`: Runs unit tests

### Running the application

We can run the application locally using Docker:

#### Build the application

```bash
docker build -t app .
```

#### Run the application

```bash
docker run -i --rm app < docs/input.txt or docker run -i --rm app // after input the data
```

It is also possible to run the application using [IntelliJ IDEA](https://www.jetbrains.com/idea/).

### Testing

We have some gradle tasks to run testing. The following command runs the application build and tests:

```bash
./gradlew clean build test
```

### Dependencies

* **Gradle**: Dependency manager
* **Jackson**: JSON serialization/deserialization
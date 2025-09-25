FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Create allure-results directory
RUN mkdir -p allure-results

# Set default command
CMD ["mvn", "clean", "test"]
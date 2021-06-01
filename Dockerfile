FROM maven:3.6.3-jdk-11 as builder
WORKDIR /source
COPY . /source
RUN mvn clean package

FROM maven:3.6.3-jdk-11
WORKDIR /app
COPY --from=builder /source/target/datagrip.jar /app

EXPOSE 8080
CMD java -jar datagrip.jar
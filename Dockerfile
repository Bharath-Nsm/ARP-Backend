FROM maven:3-jdk-8 as builder

RUN mkdir /app
ADD . /app
WORKDIR /app
RUN mvn clean install

FROM java:8

RUN mkdir -p /app
COPY --from=builder /app/target/arpMicroService-0.0.1-SNAPSHOT.jar /app/arpMicroService.jar
#RUN sh -c 'touch /app/arpMicroService.jar'
ENTRYPOINT ["java","-jar","/app/arpMicroService.jar"]

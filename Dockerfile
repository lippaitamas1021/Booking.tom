FROM adoptopenjdk:16-jre-hotspot as builder
RUN mkdir /opt/app
COPY target/bookings-0.0.1-SNAPSHOT.jar /opt/app/bookings.jar
CMD ["java", "-jar", "/opt/app/bookings.jar"]
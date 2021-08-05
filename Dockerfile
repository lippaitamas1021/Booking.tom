#FROM adoptopenjdk:16-jre-hotspot as builder
#WORKDIR application
#COPY target/bookings-0.0.1-SNAPSHOT.jar bookings.jar
#RUN java -Djarmode=layertools -jar bookings.jar extract
#
#FROM adoptopenjdk:16-jre-hotspot
#WORKDIR application
#COPY --from=builder application/dependencies/ ./
#COPY --from=builder application/spring-boot-loader/ ./
#COPY --from=builder application/snapshot-dependencies/ ./
#COPY --from=builder application/application/ ./
#CMD ["java", \
#"org.springframework.boot.loader.JarLauncher"]
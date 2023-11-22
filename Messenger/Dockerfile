FROM openjdk:17
COPY target/messenger.jar ./
RUN mkdir "./images"
CMD ["java", "-jar", "messenger.jar"]
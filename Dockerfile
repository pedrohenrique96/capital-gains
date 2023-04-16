#ARG BUILD_HOME=/app
#FROM gradle:jdk17 as build-image
#ARG BUILD_HOME
#ENV APP_HOME=$BUILD_HOME
#WORKDIR $APP_HOME
#COPY --chown=gradle:gradle build.gradle.kts settings.gradle.kts $APP_HOME/
#COPY --chown=gradle:gradle src $APP_HOME/src
#RUN gradle build
#
#FROM openjdk:17-alpine
#ARG BUILD_HOME
#ENV APP_HOME=$BUILD_HOME
#COPY --from=build-image $APP_HOME/build/libs/account-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-Dserver.port=8080", "-Dserver.address=0.0.0.0", "-jar", "app.jar"]

ARG BUILD_HOME=/app
FROM gradle:openjdk:11-alphane as build-image
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
WORKDIR $APP_HOME
COPY --chown=gradle:gradle build.gradle.kts settings.gradle.kts $APP_HOME/
COPY --chown=gradle:gradle src $APP_HOME/src
RUN gradle build

FROM openjdk:11
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
COPY --from=build-image $APP_HOME/build/libs/tests-to-nu-1.0-SNAPSHOT.jar app.jar

# Install readline library
RUN apk add --no-cache readline

# Set the entrypoint to launch the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Set the command to execute the application
CMD ["-interactive"]
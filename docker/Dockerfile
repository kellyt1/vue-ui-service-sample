FROM adoptopenjdk/openjdk11:alpine-jre
ARG profile
ENV spring_profiles_active=$profile
COPY service.jar .
EXPOSE 8080
CMD java -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar service.jar
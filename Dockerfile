FROM gradle:9.0-jdk17-alpine as builder

WORKDIR /build
COPY ./ /build

RUN gradle shadowjar

FROM amazoncorretto:17.0.16-alpine3.21

WORKDIR /home/GravenSupport
COPY --from=builder /build/build/libs/*.jar /GravenSupport.jar
VOLUME /home/GravenSupport/config.yml

ENTRYPOINT ["java","--enable-preview","-jar","/GravenSupport.jar"]
FROM gradle:9.0.0-jdk17-corretto-al2023 AS builder

WORKDIR /build
COPY ./ /build

RUN gradle shadowjar

FROM amazoncorretto:21.0.8-alpine3.21

WORKDIR /home/GravenSupport
COPY --from=builder /build/build/libs/*.jar /GravenSupport.jar
VOLUME /home/GravenSupport/config.yml

ENTRYPOINT ["java","--enable-preview","-jar","/GravenSupport.jar"]
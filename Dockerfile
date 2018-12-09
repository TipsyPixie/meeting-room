FROM openjdk:8
LABEL maintainer="Seongjae Hwang <lotsofluck4m@gmail.com>"

ENV ROOT /apps/meeting_room

RUN mkdir -p $ROOT

WORKDIR $ROOT

COPY . .

EXPOSE 80

CMD ./gradlew bootRun

FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

# Add a usrdeploy user to run our application so that it doesn't need to run as root
RUN adduser -D -s /bin/sh usrdeploy
WORKDIR /home/usrdeploy

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown usrdeploy:usrdeploy entrypoint.sh
USER usrdeploy

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 8081

ADD *.war app.war


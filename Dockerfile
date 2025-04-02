FROM openjdk:8-jre-alpine

MAINTAINER pinteh 794409767@qq.com

ADD app.jar /opt/ant-live/app.jar

ENTRYPOINT ["java", "-jar", "/opt/ant-live/app.jar"]

EXPOSE 8080
FROM tomcat:8.5.54-jdk11-openjdk-slim
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/frontendframeworks_angular_webapp.war /usr/local/tomcat/webapps/frontendframeworks_angular_webapp.war
EXPOSE 8081
CMD ["catalina.sh","run"]

FROM tomcat:8.0-jre8-alpine

COPY ./target/popula-resultados-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]
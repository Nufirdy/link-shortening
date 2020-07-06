FROM tomcat:9-jdk8
COPY target/ROOT.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]

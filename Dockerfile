FROM openjdk:8
EXPOSE 8084
ADD target/warehousetest-docker.jar warehousetest-docker.jar
ENTRYPOINT ["java","-jar","/warehousetest-docker.jar"]
#
#COPY target/classes/com/example/warehousetest /tmp
#WORKDIR /tmp
##CMD java WarehousetestApplication
#CMD java -classpath target/classes/com/example/warehousetest WarehousetestApplication.class
FROM openjdk:8
COPY target/classes/com/example/warehousetest /tmp
WORKDIR /tmp
CMD java WarehousetestApplication.class
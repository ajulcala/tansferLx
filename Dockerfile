FROM openjdk:11
VOLUME /tmp
EXPOSE 8021
ADD ./target/transfer-0.0.1-SNAPSHOT.jar transfer.jar
ENTRYPOINT ["java","-jar","/transfer.jar"]
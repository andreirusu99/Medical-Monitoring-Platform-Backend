FROM maven:3.6.3-jdk-11 AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn package -DskipTests
RUN java -Djarmode=layertools -jar /root/target/*.jar list
RUN java -Djarmode=layertools -jar /root/target/*.jar extract
RUN ls -l /root

FROM openjdk:11.0.6-jre

ENV TZ=UTC
ENV DB_IP=ec2-54-170-123-247.eu-west-1.compute.amazonaws.com
ENV DB_PORT=5432
ENV DB_USER=oluaccpzhhwczm
ENV DB_PASSWORD=0e832aacc9e92371e04c78c3553ed42796fb2c739f84166ac1dfd6c4ffe54811
ENV DB_DBNAME=d7vfdd9q0f97so


COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]

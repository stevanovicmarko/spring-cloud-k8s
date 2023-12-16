FROM eclipse-temurin:17-jre@sha256:78edb0ed4d685d6e25ec9c49cd17850e86522a2db8766cf79730b044a19b399a AS builder
WORKDIR workspace
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} catalog-service.jar
RUN java -Djarmode=layertools -jar catalog-service.jar extract

FROM eclipse-temurin:17-jre@sha256:78edb0ed4d685d6e25ec9c49cd17850e86522a2db8766cf79730b044a19b399a
RUN useradd -ms /bin/bash spring
USER spring
WORKDIR workspace
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]

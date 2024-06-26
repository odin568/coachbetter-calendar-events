FROM eclipse-temurin:21-jdk AS builder
WORKDIR application
COPY build/libs/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract


FROM eclipse-temurin:21-jre
LABEL maintainer="odin568"
EXPOSE 8080

# Set timezone
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Europe/Berlin
RUN apt-get update && apt-get install --no-install-recommends -y tzdata && apt-get clean && rm -rf /var/lib/apt/lists/*
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Create user/group
RUN groupadd --gid 1000 appgroup && \
    useradd -rm -d /home/appuser -s /bin/bash -g appgroup -G sudo -u 1000 appuser

# Create and own directory
RUN mkdir application && chown -R appuser:appgroup ./application
USER appuser
WORKDIR application

# Copy application from builder stage
COPY --chown=appuser:appgroup --from=builder application/dependencies/ ./
COPY --chown=appuser:appgroup --from=builder application/spring-boot-loader/ ./
COPY --chown=appuser:appgroup --from=builder application/snapshot-dependencies/ ./
COPY --chown=appuser:appgroup --from=builder application/application/ ./

# Set entrypoint
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
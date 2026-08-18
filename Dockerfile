# --- Stage 1: build JAR ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
# využij cache: nejdřív jen pom.xml
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# teď zdrojáky
COPY src ./src
RUN mvn -q -DskipTests package

# --- Stage 2: run ---
FROM eclipse-temurin:21-jre
WORKDIR /app
# zkopírujeme vzniklý jar (název nemusíš znát přesně)
COPY --from=build /app/target/*SNAPSHOT.jar app.jar
ENV JAVA_TOOL_OPTIONS="-Xms128m -Xmx768m"
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]


FROM openjdk:8
ADD target/studentvacancyallocater-0.0.1-SNAPSHOT.jar studentvacancyallocater-0.0.1-SNAPSHOT.jar
EXPOSE 8788
ENTRYPOINT ["java", "-jar", "studentvacancyallocater-0.0.1-SNAPSHOT.jar"]
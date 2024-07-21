# Utiliser l'image officielle de Tomcat 9
FROM tomcat:9.0

# Installer Java 1.8
RUN apt-get update && apt-get install -y openjdk-8-jdk && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copier la webapp dans le répertoire webapps de Tomcat
COPY target/user-management-app-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/app.war

# Définir un argument pour le profil Spring avec une valeur par défaut
ARG SPRING_PROFILE=qa

# Définir JAVA_OPTS avec le profil Spring
ENV JAVA_OPTS="-Dspring.profiles.active=${SPRING_PROFILE}"

# Démarrer Tomcat avec les options Java
CMD ["catalina.sh", "run"]



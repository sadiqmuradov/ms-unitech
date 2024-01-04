FROM openjdk:17-jdk-slim
ARG ENV
ENV DEPLOY_ENV=$ENV
ENV TZ=Asia/Baku
COPY ms-unitech.jar ./app.jar
#EXPOSE 8084
ENTRYPOINT ["java", "-jar", "./app.jar", "-Duser.timezone=${TZ}", "--spring.profiles.active=${DEPLOY_ENV}"]
CMD [""]

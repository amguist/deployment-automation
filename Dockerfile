FROM jenkins/jenkins:lts
USER root
RUN apt-get update && apt-get install -y maven && apt-get install -y vim
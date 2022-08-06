FROM gradle:jdk11
FROM ubuntu:xenial

ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get update && apt-get -y upgrade
RUN apt-get -y install git redis-server wget

RUN apt-get install -y openjdk-8-jdk
RUN apt-get install ca-certificates-java
RUN update-ca-certificates -f

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
RUN export JAVA_HOME

RUN apt-get install -y gnupg
RUN wget -qO - https://www.mongodb.org/static/pgp/server-4.2.asc | apt-key add -
RUN echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/4.2 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-4.2.list
RUN apt-get install -y apt-transport-https ca-certificates
RUN apt-get update
RUN apt-get install -y mongodb-org
USER root

RUN mkdir code
COPY . /code

RUN cd /code && ./gradlew bootjar

CMD /code/start.sh
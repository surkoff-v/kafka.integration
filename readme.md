## 1. kafka with zookeeper and kafdrop without docker  

````
d:\java\tools\kafka\kafka_2.13-2.8.0>.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

d:\java\tools\kafka\kafka_2.13-2.8.0>.\bin\windows\kafka-server-start.bat .\config\server.properties

d:\java\tools>java -jar kafdrop-3.27.0.jar --kafka.brokerConnect=localhost:9092
````

Once started, you can open Kafdrop in your browser by navigating to localhost:9000


## 2. kafka without zookeeper (KRaft) and kafdrop with docker
see docker-compose.yml 

to run some scripts from client in the same docker network from another container use  

docker run -it --rm --network dockerscripts_broker-kafka/
bitnami/kafka:latest kafka-topics.sh --list/
--bootstrap-server kafka:9092

if you want to connet to kafka from windows using CLI you have to dowload https://kafka.apache.org/downloads
latest binary download unpack them and add <your kafka dir>kafka_<version>\bin\windows to your path 

then you can run 

kafka-topics.bat --list --bootstrap-server 127.0.0.1:9094



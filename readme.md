## 1. kafka with zookeeper and kafdrop without docker  

````
d:\java\tools\kafka\kafka_2.13-2.8.0>.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

d:\java\tools\kafka\kafka_2.13-2.8.0>.\bin\windows\kafka-server-start.bat .\config\server.properties

d:\java\tools>java -jar kafdrop-3.27.0.jar --kafka.brokerConnect=localhost:9092
````

Once started, you can open Kafdrop in your browser by navigating to localhost:9000


## 2. kafka without zookeeper (KRaft) and kafdrop with docker
see docker-compose.yml 



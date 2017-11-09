# ARP-Backend
Back-end services for Automatic Request Processing system. 

You need Docker and Cassandra DB to run this back-end service. After setting up the above modules

Docker file for Cassandra db configuration and spring boot configurations are made for auto set-up and you can find in ~/cassandra

Cassandra Docker image
======================
Building (Run for project root dir)
-----------------------------------
docker build -t mycassandra cassandra/.

Running
-------
docker run -d --name cassandra -p 9042:9042 mycassandra

Spring Boot Docker image
========================
Building
--------
docker build -t springboot

Running
-------
docker run --name myapp --link mycassandra:db -p 8082:8080 springboot

Cassandra db operation can be done using the cassandra command promt
====================================================================
docker exec -it mycassandra bash
cassandra$ ./bin/cqlsh
cqlsh> use arpdb;


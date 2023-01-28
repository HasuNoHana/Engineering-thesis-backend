#!/bin/bash
docker-compose -f database/stack.yml up
mysql -h localhost -P 3306 --protocol=tcp -u root -pdupa --execute="
create database db_example;
create user 'springuser'@'%' identified by 'ThePassword';
grant all on db_example.* to 'springuser'@'%';
"
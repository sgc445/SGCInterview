1.This project used to get listNames attributes node Jason object
2. Need to configure the mysql database to write the json object based on query

Unit test written inside test/java directory run getCredentialsTest method

MYsql query for database operation:

show databases;
use ndsuguiddb;
drop Table listnametable;

CREATE TABLE listnametable (
recordNumber int NOT NULL AUTO_INCREMENT,
number int,
given varchar(255),
guid varchar(255),
sn varchar(255),
username varchar(255),
PRIMARY KEY (recordNumber)

);
select * from listnameTable;

This code can be optimized and java doc can be created with method usages.
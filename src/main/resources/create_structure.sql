create role userdb with password '123456';
create database bd_invest owner userdb;
ALTER ROLE userdb LOGIN;



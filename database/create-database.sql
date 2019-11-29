create database fchaindb default character set utf8;

create user 'mots'@'localhost' identified by '1234';
create user 'mots'@'%' identified by '1234';

grant all privileges on *.* to 'mots'@'localhost';
grant all privileges on *.* to 'mots'@'%';

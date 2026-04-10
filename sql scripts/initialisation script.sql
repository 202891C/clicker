#clicker
-- create database if not exists clicker;
-- show databases;

use clicker;
-- drop table if exists responses; 
-- create table responses (id int not null primary key auto_increment,questionNo int, choice varchar(1));
-- ALTER TABLE responses AUTO_INCREMENT=1001;
# table: responses
show tables;
-- INSERT INTO responses (questionNo, choice) VALUES (1, 'A');
-- INSERT INTO responses (questionNo, choice) VALUES (1, 'B');
-- INSERT INTO responses (questionNo, choice) VALUES (1, 'C');
-- INSERT INTO responses (questionNo, choice) VALUES (1, 'D');
SELECT count(*) AS A FROM responses WHERE questionNo=1 AND choice='A'; 
SELECT count(*) AS B FROM responses WHERE questionNo=1 AND choice='B'; 
SELECT count(*) AS C FROM responses WHERE questionNo=1 AND choice='C'; 
SELECT count(*) AS D FROM responses WHERE questionNo=1 AND choice='D'; 
select * from responses;
SELECT choice, COUNT(*) AS count FROM responses
 WHERE questionNo=1 GROUP BY choice; 
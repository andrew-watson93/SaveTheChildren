drop table if exists customer;
create table customer
(
   id integer not null auto_increment,
   firstname varchar(255) not null,
   secondname varchar(255) not null,
   email varchar(255) not null,
   primary key(id)
);
drop table if exists client;

create table client (
    clientId int auto_increment,
    clientName varchar(250) not null
)

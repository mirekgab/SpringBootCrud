drop table if exists client;

create table client (
    client_id int auto_increment,
    client_name varchar(250) not null
)

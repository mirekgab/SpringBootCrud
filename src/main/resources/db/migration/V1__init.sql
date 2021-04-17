drop table if exists client;
drop table if exists products;
drop table if exists orders;

create table client (
    client_id int auto_increment,
    client_name varchar(250) not null
);

create table products (
    id int auto_increment,
    product_name varchar(100),
    gross numeric(10,2)
);

create table orders (
    id int auto_increment,
    client_id int,
    order_number varchar(20),
    gross numeric(10,2)
);


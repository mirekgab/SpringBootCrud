drop table if exists client;
drop table if exists products;
drop table if exists orders;

create table client (
    client_id bigint auto_increment,
    client_name varchar(250) not null
);

create table products (
    id bigint auto_increment,
    product_name varchar(100),
    gross numeric(10,2)
);

create table orders (
    id bigint auto_increment,
    client_id bigint,
    order_number varchar(20),
    gross numeric(10,2)
);

create table orders_positions (
    id bigint auto_increment,
    order_id bigint,
    product_id bigint,
    value numeric(10,2)
)
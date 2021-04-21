drop table if exists client;
drop table if exists products;
drop table if exists orders;

create table client (
    client_id bigint auto_increment,
    client_name varchar(250) not null
);

create table products (
    product_id bigint auto_increment,
    product_name varchar(100),
    gross numeric(10,2)
);

create table orders (
    id bigint auto_increment,
    client_id bigint,
    order_number varchar(20),
    gross numeric(10,2),
    foreign key (client_id) references client(client_id)
);

create table orders_positions (
    id bigint auto_increment,
    order_id bigint,
    product_id bigint,
    value numeric(10,2)
);

alter table orders_positions 
add foreign key(product_id)
references products(product_id);

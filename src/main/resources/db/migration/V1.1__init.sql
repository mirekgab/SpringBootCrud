drop table if exists client;
drop table if exists products;
drop table if exists orders;
drop table if exists orders_positions;

create table client (
    client_id bigint auto_increment,
    client_name varchar(250) not null
);

create table products (
    product_id bigint auto_increment,
    product_name varchar(100),
    price numeric(10,2)
);

create table orders (
    order_id bigint auto_increment,
    client_id bigint,
    order_number varchar(20),
    gross numeric(10,2),
    foreign key (client_id) references client(client_id)
);

create table orders_positions (
    id bigint auto_increment,
    order_id bigint,
    product_id bigint,
    quantity numeric(10,2) default 0.00,
    value numeric(10,2) default 0.00
);

alter table orders_positions 
add foreign key(product_id)
references products(product_id);

alter table orders_positions
add foreign key(order_id)
references orders(order_id)
on delete cascade

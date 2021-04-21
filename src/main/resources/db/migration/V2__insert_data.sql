insert into client(client_id, client_name) values (1, 'client1');
insert into client(client_id, client_name) values (2, 'client2');
insert into client(client_id, client_name) values (3, 'client3');
insert into client(client_id, client_name) values (4, 'client4');
insert into products(product_id, product_name, gross) values (1, 'product1', 1.00);
insert into products(product_id, product_name, gross) values (2, 'product2', 10.23);

insert into orders(id, client_id, order_number, gross) values (1L, 1L, 'order1', 0.00);

insert into orders_positions(id, order_id, product_id, value) values (1L, 1L, 1L, 0.00);

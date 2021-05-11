insert into client(client_id, client_name) 
values 
    (1, 'client1'),
    (2, 'client2'),
    (3, 'client3'),
    (4, 'client4');

insert into products(product_id, product_name, gross) values 
    (1L, 'product1', 1.00),
    (2L, 'product2', 10.23),
    (3L, 'product3', 1.22);

insert into orders(order_id, client_id, order_number, gross) 
values 
    (1L, 1L, 'order1', 0.00),
    (2L, 2L, 'order2', 1.22);

insert into orders_positions(id, order_id, product_id, value) 
values 
    (1L, 1L, 1L, 0.00),
    (2L, 1L, 2L, 1.02),
    (3L, 2L, 1L, 1.00),
    (4L, 1L, 3L, 2.22);

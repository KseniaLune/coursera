create table products(
    id serial primary key,
    name varchar(255),
    description varchar(255),
    quantity int,
    price double precision
);

insert into products (name, description, quantity, price)
values ('Carrot','just carrot', 10, 1.5);
insert into products (name, description, quantity, price)
values ('Potato','just potato', 10, 1.5);
insert into products (name, description, quantity, price)
values ('Onion','just onion', 10, 1.5);
create table customer
(
    id    int auto_increment      not null,
    name  varchar(30) not null,
    email varchar(60) not null,
    constraint customer_pk primary key (id)
);

create table restaurant
(
    id           int auto_increment not null,
    name         varchar(30)        not null,
    kitchen_type varchar(30)        not null,
    capacity     int                not null,
    opening_time time               not null,
    closing_time time               not null,

    street       varchar(60)        not null,
    number       varchar(10)        not null,
    complement   varchar(60),
    district     varchar(30)        not null,
    city         varchar(30)        not null,
    state        varchar(2)         not null,
    postal_code  varchar(8)         not null,
    constraint restaurant_pk primary key (id)
);


create table tables
(
    id            int auto_increment not null,
    number        int    not null,
    capacity      int    not null,
    restaurant_id int    not null,
    constraint tables_id primary key (id),
    foreign key (restaurant_id) references restaurant (id)
);

create table reviews
(
    id            int auto_increment not null,
    score         int    not null,
    comment       varchar(500),
    restaurant_id int    not null,
    customer_id   int    not null,
    constraint reviews_id primary key (id),
    foreign key (restaurant_id) references restaurant (id),
    foreign key (customer_id) references customer (id)
);

insert into customer (name, email)
values ('eduardo', 'eduardo@gmail.com'),
       ('maria', 'maria@gmail.com'),
       ('joao', 'joao@gmail.com');

insert into restaurant (name, kitchen_type, capacity, opening_time, closing_time, street, number, complement, district,
                        city, state, postal_code)
values ('PIZZARIA 01', 'PIZZARIA', 100, '09:00', '22:00', 'AV IMPERATRIZ', '900', null, 'CENTRO', 'São Paulo', 'SP',
        '05399000'),
       ('CHURRASCARIA 01', 'CHURRASCARIA', 500, '12:00', '20:00', 'RUA GUAIPA', '1900', null, 'Centro', 'São Paulo',
        'SP', '05399000'),
       ('SUSHI 01', 'ORIENTAL', 200, '08:00', '20:00', 'AV QUEIROS FILHO', '100', null, 'PINHEIROS', 'São Paulo', 'SP',
        '05399000')
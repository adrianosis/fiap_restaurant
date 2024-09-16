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

create table reservation
(
    id                   int auto_increment    not null,
    reservation_datetime timestamp not null,
    guests               int      not null,
    start_service        timestamp,
    end_service          timestamp,
    table_tag            varchar(10),
    status               varchar(12)      not null,
    restaurant_id        int      not null,
    customer_id          int      not null,
    constraint reservation_pk primary key (id),
    foreign key (restaurant_id) references restaurant (id),
    foreign key (customer_id) references customer (id)
);

create table review
(
    id            int auto_increment not null,
    score         int    not null,
    comment       varchar(500),
    reservation_id int    not null,
    constraint reviews_id primary key (id),
    foreign key (reservation_id) references reservation (id)
);

insert into customer (name, email)
values ('eduardo', 'eduardo@gmail.com'),
       ('maria', 'maria@gmail.com'),
       ('joao', 'joao@gmail.com');

insert into restaurant (name, kitchen_type, capacity, opening_time, closing_time, street, number, complement, district,
                        city, state, postal_code)
values ('PIZZARIA 01', 'PIZZARIA', 100, '09:00', '22:00', 'AV IMPERATRIZ', '900', null, 'CENTRO', 'São Paulo', 'SP', '05399000'),
       ('CHURRASCARIA 01', 'CHURRASCARIA', 500, '12:00', '20:00', 'RUA GUAIPA', '1900', null, 'CENTRO', 'São Paulo', 'SP', '05399000'),
       ('SUSHI 01', 'ORIENTAL', 200, '08:00', '20:00', 'AV QUEIROS FILHO', '100', null, 'PINHEIROS', 'São Paulo', 'SP', '05399000');

insert into reservation (reservation_datetime, guests, start_service, end_service, table_tag, status, restaurant_id, customer_id)
values ('2024-09-15 17:00:00', 10, '2024-09-15 17:05:00', '2024-09-15 18:30:00', 'A15', 'COMPLETED', 1, 1),
       ('2024-09-15 18:00:00', 4, '2024-09-15 18:05:00', null, 'A25', 'IN_PROGRESS', 1, 1),
       ('2024-09-15 19:00:00', 6, null, null, 'A25', 'RESERVED', 1, 1);
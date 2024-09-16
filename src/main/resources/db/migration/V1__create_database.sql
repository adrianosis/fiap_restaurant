create table customer
(
    id    serial      not null,
    name  varchar(30) not null,
    email varchar(60) not null,
    constraint customer_pk primary key (id)
);

create table restaurant
(
    id           serial      not null,
    name         varchar(30) not null,
    kitchen_type varchar(30) not null,
    capacity     int         not null,
    opening_time time        not null,
    closing_time time        not null,

    street       varchar(60) not null,
    number       varchar(10) not null,
    complement   varchar(60),
    district     varchar(30) not null,
    city         varchar(30) not null,
    state        varchar(2)  not null,
    postal_code  varchar(8)  not null,
    constraint restaurant_pk primary key (id)
);

create table reservation
(
    id                   serial    not null,
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
    id            serial not null,
    score         int    not null,
    comment       varchar(500),
    reservation_id int    not null,
    constraint reviews_id primary key (id),
    foreign key (reservation_id) references reservation (id)
);

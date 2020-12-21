create table if not exists addresses
(
    id     bigserial not null,
    street varchar,
    primary key (id)
);

create table if not exists users
(
    id         bigserial not null,
    name       varchar,
    address_id bigint,
    primary key (id),
    constraint fk_addresses foreign key (address_id) references addresses (id)
);

create table if not exists phones
(
    id      bigserial not null,
    number  varchar,
    user_id bigint,
    primary key (id),
    constraint fk_users foreign key (user_id) references users (id)
);
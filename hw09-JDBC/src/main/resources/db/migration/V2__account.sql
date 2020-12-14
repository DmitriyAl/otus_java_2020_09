create table if not exists account
(
    number   uuid primary key,
    type varchar(50),
    rest  real
);
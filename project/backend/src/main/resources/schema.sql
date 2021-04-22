drop table if exists bars;
drop table if exists orders;
drop table if exists portfolios;
drop table if exists tickers;
drop table if exists user_authority;
drop table if exists authorities;
drop table if exists users;

create table if not exists users
(
    id       bigserial primary key,
    login    varchar(255) not null,
    password varchar(255) not null
);

create table if not exists authorities
(
    id   bigserial primary key,
    name varchar(255) not null
);

create table if not exists user_authority
(
    user_id      bigint not null
        constraint users_fkey
            references users,
    authority_id bigint not null
        constraint authorities_fkey
            references authorities
);

create table if not exists tickers
(
    id             bigserial not null
        constraint tickers_pkey primary key,
    information    varchar(255),
    last_refreshed date,
    output_size    varchar(255),
    symbol         varchar(255)
        constraint unique_symbol
            unique,
    time_zone      varchar(255)
);

create table if not exists bars
(
    id                bigserial not null
        constraint bars_pkey
            primary key,
    adjusted_close    real      not null,
    close             real      not null,
    date              timestamp not null,
    dividend_amount   real      not null,
    high              real      not null,
    low               real      not null,
    open              real      not null,
    split_coefficient real      not null,
    volume            integer   not null,
    ticker_id         bigint
        constraint tickers_fkey
            references tickers
);

create table if not exists portfolios
(
    id      bigserial not null
        constraint portfolios_pkey
            primary key,
    created timestamp,
    name    varchar(255)
        constraint unique_name
            unique,
    updated timestamp,
    user_id bigint
        constraint users_fkey
            references users
);

create table if not exists orders
(
    id           bigserial not null
        constraint orders_pkey
            primary key,
    amount       integer   not null,
    executed     timestamp,
    price        real      not null,
    type         varchar(255),
    portfolio_id bigint
        constraint portfolios_fkey
            references portfolios,
    ticker_id    bigint
        constraint tickers_fkey
            references tickers
);
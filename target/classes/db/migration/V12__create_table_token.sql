create table token
(
    expired    boolean not null,
    revoked    boolean not null,
    id         bigint  not null,
    user_id    bigint,
    token      varchar(255) unique,
    token_type varchar(255) check (token_type in ('BEARER')),
    primary key (id)
)
;
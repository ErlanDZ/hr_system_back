create table image_data
(
    id         bigserial not null,
    name       varchar(255),
    path       varchar(255),
    type       varchar(255),
    image_data bytea,
    primary key (id)
)
;
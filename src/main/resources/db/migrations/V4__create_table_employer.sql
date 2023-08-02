create table employer
(
    id            bigserial not null,
    image_id      bigint unique,
    about_company varchar(255),
    address       varchar(255),
    city          varchar(255),
    company_name  varchar(255),
    country       varchar(255),
    email         varchar(255),
    password      varchar(255),
    phone_number  varchar(255),
    primary key (id)
)
;
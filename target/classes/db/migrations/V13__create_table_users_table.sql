create table users_table
(
    employer_id   bigint unique,
    id            bigserial not null,
    job_seeker_id bigint unique,
    email         varchar(255),
    firstname     varchar(255),
    image         varchar(255),
    lastname      varchar(255),
    password      varchar(255),
    role          varchar(255) check (role in ('ADMIN', 'JOB_SEEKER', 'EMPLOYER')),
    primary key (id)
)
;
create sequence token_seq start with 1 increment by 50 ;
create table categories
(
    id   bigserial not null,
    name varchar(255),
    primary key (id)
) ;
create table categories_vacancies
(
    category_id  bigint not null,
    vacancies_id bigint not null unique
) ;
create table contact_information
(
    id           bigserial not null,
    city         varchar(255),
    country      varchar(255),
    street_house varchar(255),
    primary key (id)
) ;
create table employer
(
    id            bigserial not null,
    about_company varchar(255),
    address       varchar(255),
    city          varchar(255),
    company_name  varchar(255),
    country       varchar(255),
    email         varchar(255),
    password      varchar(255),
    phone_number  varchar(255),
    image         bytea,
    primary key (id)
) ;
create table employer_favorites
(
    employers_id bigint not null,
    favorites_id bigint not null
) ;
create table experience
(
    id   bigserial not null,
    name varchar(255),
    primary key (id)
) ;
create table image_data
(
    id         bigserial not null,
    name       varchar(255),
    type       varchar(255),
    image_data bytea,
    primary key (id)
) ;
create table job_seeker_table
(
    birthday        date,
    education       smallint check (education between 0 and 5),
    graduation_date date,
    month           smallint check (month between 0 and 11),
    until_now       boolean   not null,
    year            date,
    id              bigserial not null,
    image_id        bigint unique,
    is_favorite     bigint,
    about           varchar(255),
    address         varchar(255),
    city            varchar(255),
    country         varchar(255),
    email           varchar(255),
    firstname       varchar(255),
    institution     varchar(255),
    lastname        varchar(255),
    password        varchar(255),
    phone_number    varchar(255),
    position        varchar(255),
    resume          varchar(255),
    rol             varchar(255) check (rol in ('ADMIN', 'JOB_SEEKER', 'EMPLOYER')),
    skills          varchar(255),
    working_place   varchar(255),
    primary key (id)
) ;
create table job_seeker_table_vacancies
(
    job_seeker_id bigint not null,
    vacancies_id  bigint not null
) ;
create table position
(
    category_id bigint,
    id          bigserial not null,
    name        varchar(255),
    primary key (id)
) ;
create table salaries
(
    salary             float(53),
    type_of_employment smallint check (type_of_employment between 0 and 2),
    valute             smallint check (valute between 0 and 5),
    id                 bigserial not null,
    primary key (id)
) ;
create table token
(
    expired    boolean not null,
    revoked    boolean not null,
    id         bigint  not null,
    user_id    bigint,
    token      varchar(255) unique,
    token_type varchar(255) check (token_type in ('BEARER')),
    primary key (id)
) ;
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
) ;
create table vacancies
(
    is_response            boolean,
    response               integer,
    status_of_vacancy      smallint check (status_of_vacancy between 0 and 2),
    contact_information_id bigint unique,
    date                   timestamp(6),
    employer_id            bigint,
    employers_id           bigint,
    id                     bigserial not null,
    position_id            bigint,
    salary_id              bigint unique,
    contact_info           varchar(255),
    description            varchar(255),
    name                   varchar(255),
    skills                 varchar(255),
    primary key (id)
) ;
create table vacancies_job_seekers
(
    job_seekers_id bigint not null,
    vacancy_id     bigint not null
) ;
alter table if exists categories_vacancies
    add constraint FKvvmk0brsaqyoek0abib74xic foreign key (vacancies_id) references vacancies ;
alter table if exists categories_vacancies
    add constraint FKe6ctc5s7k20pugqu4uf4evrqu foreign key (category_id) references categories ;
alter table if exists employer_favorites
    add constraint FK9dy3syjvkojvkex5gfvxrrqjk foreign key (favorites_id) references job_seeker_table ;
alter table if exists employer_favorites
    add constraint FKdo81iey0sh0ovscx5h5b4ef5c foreign key (employers_id) references employer ;
alter table if exists job_seeker_table
    add constraint FK415ebajp45xbht0bf4ljn7f2r foreign key (image_id) references image_data ;
alter table if exists job_seeker_table_vacancies
    add constraint FKm6k1lx1bxj6wy2ktpiet80obf foreign key (vacancies_id) references vacancies ;
alter table if exists job_seeker_table_vacancies
    add constraint FK44lghaylk6s85kv7u62fk09tu foreign key (job_seeker_id) references job_seeker_table ;
alter table if exists position
    add constraint FK5ldqv9mghmop1efnpqdm4j4rh foreign key (category_id) references categories ;
alter table if exists token
    add constraint FKfqwpt67padn81jdnsa5e8qlh6 foreign key (user_id) references users_table ;
alter table if exists users_table
    add constraint FKsm1hxi4uhbi4v6o6w00fr5opf foreign key (employer_id) references employer ;
alter table if exists users_table
    add constraint FKoirnb6ic7xd01mqjhq7gio94i foreign key (job_seeker_id) references job_seeker_table ;
alter table if exists vacancies
    add constraint FKp60ht8jhtjutdgmhs9c6lg4ya foreign key (contact_information_id) references contact_information ;
alter table if exists vacancies
    add constraint FK2hy77i9hkv6hntcqogfkwadys foreign key (employer_id) references employer ;
alter table if exists vacancies
    add constraint FKq070b22i1hcvhgk0wlyvqat8d foreign key (position_id) references position ;
alter table if exists vacancies
    add constraint FK4wp7dfvu0l04i8sinprqhofxg foreign key (salary_id) references salaries ;
alter table if exists vacancies_job_seekers
    add constraint FKgj502hlg7gi1kb86lpsk61erg foreign key (job_seekers_id) references job_seeker_table ;
alter table if exists vacancies_job_seekers
    add constraint FKt6179hiq5n5rm4p57ovgei6wc foreign key (vacancy_id) references vacancies;

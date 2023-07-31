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
    image_id               bigint unique,
    position_id            bigint,
    salary_id              bigint unique,
    contact_info           varchar(255),
    description            varchar(255),
    name                   varchar(255),
    skills                 varchar(255),
    primary key (id)
)
;
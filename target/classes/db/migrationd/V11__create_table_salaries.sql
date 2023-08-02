create table salaries
(
    salary             float(53),
    type_of_employment smallint check (type_of_employment between 0 and 2),
    valute             smallint check (valute between 0 and 5),
    id                 bigserial not null,
    primary key (id)
)
;
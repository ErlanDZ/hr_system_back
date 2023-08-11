create table salaries (salary_sum float(53), salary_type smallint check (salary_type between 0 and 2), valute smallint check (valute between 0 and 5), id bigserial not null, primary key (id))
;
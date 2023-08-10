
alter table if exists vacancies_job_seekers add constraint FKgj502hlg7gi1kb86lpsk61erg foreign key (job_seekers_id) references job_seeker_table
; alter table if exists vacancies_job_seekers add constraint FKt6179hiq5n5rm4p57ovgei6wc foreign key (vacancy_id) references vacancies;
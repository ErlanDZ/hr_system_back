
 alter table if exists job_seeker_table_vacancies add constraint FKm6k1lx1bxj6wy2ktpiet80obf foreign key (vacancies_id) references vacancies
; alter table if exists job_seeker_table_vacancies add constraint FK44lghaylk6s85kv7u62fk09tu foreign key (job_seeker_id) references job_seeker_table;
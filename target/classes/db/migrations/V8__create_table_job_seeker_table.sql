create table job_seeker_table
(
    birthday        date,
    education       smallint check (education between 0 and 5),
    graduation_date date,
    month           smallint check (month between 0 and 11
) , until_now boolean not null, year date, experience_id bigint, id bigserial not null, image_id bigint unique, is_favorite bigint, position_id bigint, resume_id bigint unique, about varchar(255), address varchar(255), city varchar(255), country varchar(255), email varchar(255), firstname varchar(255), institution varchar(255), lastname varchar(255), password varchar(255), phone_number varchar(255), rol varchar(255) check (rol in ('ADMIN','JOB_SEEKER','EMPLOYER')), skills varchar(255), status_of_job_seeker varchar(255) check (status_of_job_seeker in ('ACCEPT','REJECT','CONSIDERED','AN_INTERVIEW','OFFER')), working_place varchar(255), primary key (id))
;
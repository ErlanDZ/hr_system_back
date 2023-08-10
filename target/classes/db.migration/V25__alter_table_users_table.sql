
alter table if exists users_table add constraint FKsm1hxi4uhbi4v6o6w00fr5opf foreign key (employer_id) references employer
; alter table if exists users_table add constraint FKoirnb6ic7xd01mqjhq7gio94i foreign key (job_seeker_id) references job_seeker_table;
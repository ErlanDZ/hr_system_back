
 alter table if exists job_seeker_table add constraint FKccskwyyx8lxlm7jitdw3n0mk4 foreign key (experience_id) references experience
; alter table if exists job_seeker_table add constraint FKjoo09mvqldikrn35lqau03eff foreign key (position_id) references position
; alter table if exists job_seeker_table add constraint FKs6dexyfft2oi1o1goa0j0pr7h foreign key (resume_id) references file_data;
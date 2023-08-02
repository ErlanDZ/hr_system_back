alter table if exists job_seeker_table add constraint FKccskwyyx8lxlm7jitdw3n0mk4 foreign key (experience_id) references experience
;
alter table if exists job_seeker_table add constraint FK415ebajp45xbht0bf4ljn7f2r foreign key (image_id) references image_data
;
alter table if exists job_seeker_table add constraint FKjoo09mvqldikrn35lqau03eff foreign key (position_id) references position
;
alter table if exists job_seeker_table add constraint FKgy49oihkbu3cse89jsacay09d foreign key (resume_id) references image_data
;
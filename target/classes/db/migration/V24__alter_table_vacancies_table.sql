alter table if exists vacancies add constraint FKp60ht8jhtjutdgmhs9c6lg4ya foreign key (contact_information_id) references contact_information
;
alter table if exists vacancies add constraint FK2hy77i9hkv6hntcqogfkwadys foreign key (employer_id) references employer
;
alter table if exists vacancies add constraint FKoxj6y30b3apucsovhhvxbofj6 foreign key (image_id) references image_data
;
alter table if exists vacancies add constraint FKq070b22i1hcvhgk0wlyvqat8d foreign key (position_id) references position
;
alter table if exists vacancies add constraint FK4wp7dfvu0l04i8sinprqhofxg foreign key (salary_id) references salaries
;
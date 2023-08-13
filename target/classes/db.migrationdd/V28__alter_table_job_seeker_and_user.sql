ALTER TABLE notification
    ADD COLUMN user_id BIGINT;


UPDATE notification AS n
SET user_id = n.user_id FROM job_seeker_table AS js
WHERE n.job_seeker = js.id;


ALTER TABLE notification
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES job_seeker_table;


ALTER TABLE notification DROP COLUMN job_seeker;


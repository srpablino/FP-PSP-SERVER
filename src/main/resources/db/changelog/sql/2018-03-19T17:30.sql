--Adds activity message
ALTER TABLE system.activity
  ADD COLUMN activity_key character varying(200) NOT NULL,
  ADD COLUMN activity_params character varying(200) NOT NULL,
  ADD COLUMN activity_role character varying(200) NOT NULL
;

--Deletes unused columns of activity
ALTER TABLE system.activity
  DROP COLUMN activity_type CASCADE,
  DROP COLUMN description CASCADE;



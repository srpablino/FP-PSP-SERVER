--Adds activity message
ALTER TABLE system.activity
  ADD COLUMN family_id bigint,
  ADD COLUMN activity_key character varying(200) NOT NULL,
  ADD COLUMN activity_params character varying(200) NOT NULL;

--Add activity with family relationships
ALTER TABLE system.activity
  ADD CONSTRAINT fk_activity_feed_family_family_id FOREIGN KEY (family_id)
  REFERENCES ps_families.family (family_id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION;

--Deletes unused columns of activity
ALTER TABLE system.activity
  DROP COLUMN activity_type CASCADE,
  DROP COLUMN description CASCADE;



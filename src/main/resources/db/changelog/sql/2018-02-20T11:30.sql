--Add column to save if priority is success, is_success to NOT NULL

ALTER TABLE data_collect.snapshot_indicator_priorities
    ADD COLUMN is_success boolean;

UPDATE data_collect.snapshot_indicator_priorities SET is_success = false WHERE is_success IS NULL;


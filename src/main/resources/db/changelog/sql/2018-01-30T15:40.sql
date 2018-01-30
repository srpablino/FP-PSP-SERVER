--Change columns: name, code, description, application_id, is_active to NOT NULL
ALTER TABLE security.users ALTER COLUMN email SET NOT NULL;
ALTER TABLE security.users ALTER COLUMN pass SET NOT NULL;
ALTER TABLE security.users ALTER COLUMN active SET NOT NULL;

-- Run this with psql -U YourUser -f create_database.sql
CREATE database "fp_psp_db";
SELECT datname FROM pg_database WHERE datistemplate = false;
CREATE USER "fp_psp_db" WITH ENCRYPTED PASSWORD 'fp_psp_db';
GRANT ALL PRIVILEGES ON DATABASE "fp_psp_db" to "fp_psp_db";
ALTER DATABASE "fp_psp_db" OWNER TO "fp_psp_db";
\c "fp_psp_db";
CREATE SCHEMA "fp_psp_db";
GRANT ALL PRIVILEGES ON SCHEMA "fp_psp_db" to "fp_psp_db";

CREATE TABLE data_collect.survey_socioeconomics (
	  encuesta_semaforo_id bigserial PRIMARY KEY,
	  zona VARCHAR(200),
	  acteconomica_primaria VARCHAR(200),
	  acteconomica_secundaria VARCHAR(200),
	  salario_mensual double precision,
	  odk_row_reference_id bigint
);

CREATE TABLE data_collect.odk_row_references (
	  odk_row_reference_id bigserial PRIMARY KEY,
	  odk_table_id VARCHAR(200),
	  odk_schema_etag VARCHAR(200),
	  odk_row_id VARCHAR(200)
);


ALTER TABLE data_collect.survey_socioeconomics
  ADD CONSTRAINT fk_survey_socioeconomics_odk_row_reference_id
  FOREIGN KEY (odk_row_reference_id)
  REFERENCES data_collect.odk_row_references (odk_row_reference_id);

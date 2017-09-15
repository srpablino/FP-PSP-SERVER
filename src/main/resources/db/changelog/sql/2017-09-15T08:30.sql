
CREATE TABLE data_collect.survey_socioeconomics (
	  encuesta_semaforo_id bigserial PRIMARY KEY,
	  zona VARCHAR(200),
	  acteconomica_primaria VARCHAR(200),
	  acteconomica_secundaria VARCHAR(200),
	  salario_mensual double precision,
	  survey_indicator_id bigint
);

CREATE TABLE data_collect.survey_indicators (
	  survey_indicator_id bigserial PRIMARY KEY,
	  odk_table_reference VARCHAR(200)
);


ALTER TABLE data_collect.survey_socioeconomics
  ADD CONSTRAINT fk_survey_socioeconomics_survey_indicator_id
  FOREIGN KEY (survey_indicator_id)
  REFERENCES data_collect.survey_indicators (survey_indicator_id);

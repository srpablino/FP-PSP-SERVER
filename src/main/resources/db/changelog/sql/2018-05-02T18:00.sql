INSERT INTO system.parameter(
           parameter_id, key_parameter, description, type_parameter, value)
   VALUES (nextval('system.parameter_parameter_id_seq'), 'minimum_priority', 'Minimum priority amount for the life map', 'Integer', 3);
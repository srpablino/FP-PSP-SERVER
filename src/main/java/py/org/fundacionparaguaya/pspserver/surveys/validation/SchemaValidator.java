package py.org.fundacionparaguaya.pspserver.surveys.validation;

import py.org.fundacionparaguaya.pspserver.web.models.SurveySchema;

/**
 * Created by rodrigovillalba on 10/24/17.
 */
public interface SchemaValidator extends SchemaValidatorFunction<SurveySchema, String, Object, ValidationResult> {


    static SchemaValidator requiredValue() {
        return (SurveySchema schema, String propertyName, Object propertyValue) -> {
            if (schema.getRequired().contains(propertyName) && propertyValue == null) {
                return ValidationResult.invalid(propertyName, "Property '" + propertyName + "' is required");

            }
            return ValidationResult.valid();
        };
    }

    static SchemaValidator presentInSchema() {
        return (SurveySchema schema, String propertyName, Object propertyValue) -> {
            if (!schema.getProperties().containsKey(propertyName)) {
                return ValidationResult.invalid(propertyName, "Property '" + propertyName + "' is not in survey schema");
            }
            return ValidationResult.valid();
        };
    }


    static SchemaValidator markedAsRequired() {
        return (SurveySchema schema, String propertySchemaName, Object value) -> {
            if (!schema.getRequired().contains(propertySchemaName)) {
                return ValidationResult.invalid(propertySchemaName, "Property '" + propertySchemaName + "' should be marked as required in schema");
            }

            return ValidationResult.valid();
        };
    }

}

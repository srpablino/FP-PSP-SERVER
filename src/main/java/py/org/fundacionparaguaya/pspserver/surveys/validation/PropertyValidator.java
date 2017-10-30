package py.org.fundacionparaguaya.pspserver.surveys.validation;

import py.org.fundacionparaguaya.pspserver.web.models.Property;

import static py.org.fundacionparaguaya.pspserver.surveys.validation.ValidationResult.invalid;
import static py.org.fundacionparaguaya.pspserver.surveys.validation.ValidationResult.valid;

/**
 * Created by rodrigovillalba on 10/24/17.
 */
public interface PropertyValidator extends PropertyValidatorFunction<Property, String, Object, ValidationResult> {


    static PropertyValidator validType() {
        return (Property property, String propertyName, Object propertyValue) -> {
            if (property.valueIsOfValidType(propertyValue)) {
                return valid();
            }
            return invalid(propertyName, "Property '" + propertyName + "' is of invalid type. Required: '" + property.getType() + "'");
        };
    }

}

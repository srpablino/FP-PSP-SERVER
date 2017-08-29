package py.org.fundacionparaguaya.pspserver.common.dtos;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * View Model for transferring error message with a list of field errors.
 */
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String description;

    private List<FieldErrorDTO> fieldErrors;

    public static ErrorDTO fromStatus(ResponseStatus responseStatus, String message) {
        return new ErrorDTO("error." + responseStatus.value().value(), message);
    }

    public static ErrorDTO fromCode(String code, String message) {
        return new ErrorDTO(code, message);
    }


    public static ErrorDTO fromCode(String errValidation) {
        return new ErrorDTO(errValidation);
    }

    private ErrorDTO(String message) {
        this(message, null);
    }

    private ErrorDTO(String message, String description) {
        this.message = message;
        this.description = description;
    }

    private ErrorDTO(String message, String description, List<FieldErrorDTO> fieldErrors) {
        this.message = message;
        this.description = description;
        this.fieldErrors = fieldErrors;
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDTO(objectName, field, message));
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }


}

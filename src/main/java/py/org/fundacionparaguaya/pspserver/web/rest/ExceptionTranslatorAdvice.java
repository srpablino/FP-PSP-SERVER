package py.org.fundacionparaguaya.pspserver.web.rest;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import py.org.fundacionparaguaya.pspserver.common.constants.ErrorCodes;
import py.org.fundacionparaguaya.pspserver.common.dtos.ErrorDTO;
import py.org.fundacionparaguaya.pspserver.common.dtos.ParameterizedErrorDTO;
import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;

/**
 * This controller advice is inspired by JHipster Example app:
 * https://github.com/jhipster/jhipster-sample-app
 * <p>
 *     Controller advice to translate the server side exceptions to client-friendly json structures.
 * </p>
 */
@ControllerAdvice
public class ExceptionTranslatorAdvice {

    private final Logger LOG = LoggerFactory.getLogger(ExceptionTranslatorAdvice.class);

    @ExceptionHandler(ConcurrencyFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorDTO processConcurrencyError(ConcurrencyFailureException ex) {
        return ErrorDTO.fromCode(ErrorCodes.ERR_CONCURRENCY_FAILURE);
    }

    /**
     * <p>
     *     Errors thrown in Controllers methods with arguments annotated with JAX-RS @Valid.
     * </p>
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO processControllerValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ErrorDTO dto = ErrorDTO.fromCode(ErrorCodes.ERR_VALIDATION);
        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        }
        return dto;
    }

    /**
     * <p>
     *     Errors thrown in service layers validations, using standard exceptions.
     * </p>
     * <p>
     *    We favor the use of standard exceptions as recommended in:
     *    https://github.com/tatsuyaoiw/effective-java/blob/master/chapter-9.md#item-60-favor-the-use-of-standard-exceptions
     * </p>
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO processBusinessValidationError(IllegalArgumentException ex) {
        LOG.debug(ex.getMessage(), ex);
        return ErrorDTO.fromCode(ErrorCodes.ERR_VALIDATION, ex.getMessage());
    }

    @ExceptionHandler(CustomParameterizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ParameterizedErrorDTO processParameterizedValidationError(CustomParameterizedException ex) {
        return ex.getErrorDTO();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorDTO processAccessDeniedException(AccessDeniedException e) {
        return ErrorDTO.fromCode(ErrorCodes.ERR_ACCESS_DENIED, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorDTO processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return ErrorDTO.fromCode(ErrorCodes.ERR_METHOD_NOT_SUPPORTED, exception.getMessage());
    }

    /**
     * <p>
     *     Any other unhandled exceptions are processed here.
     * </p>
     * <p>
     *     We also process user defined (non standard) exceptions annotated with @ResponseStatus.
     *     See {@link py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException}
     * </p>
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> processException(Exception ex) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("An unexpected error occurred: {}", ex.getMessage(), ex);
        } else {
            LOG.error("An unexpected error occurred: {}", ex.getMessage());
        }
        BodyBuilder builder;
        ErrorDTO errorDto;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorDto = ErrorDTO.fromStatus(responseStatus, ex.getMessage());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            errorDto = ErrorDTO.fromCode(ErrorCodes.ERR_INTERNAL_SERVER_ERROR, "Internal server error");
        }
        return builder.body(errorDto);
    }
}

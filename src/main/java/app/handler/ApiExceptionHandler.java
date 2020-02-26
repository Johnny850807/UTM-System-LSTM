package app.handler;

import app.handler.exception.InvalidRequestException;
import app.handler.exception.NotFoundException;
import app.response.ErrorResponse;
import app.response.RequestErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNotFound(NotFoundException e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidRequestException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidRequest(InvalidRequestException e){
        List<ErrorField> errorFields = new ArrayList<>();
        List<FieldError> fieldErrors = e.getErrors();
        fieldErrors.forEach(fieldError -> {
            errorFields.add(new ErrorField(fieldError.getField(), fieldError.getDefaultMessage()));
        });
        RequestErrorResponse errorResponse = new RequestErrorResponse(errorFields, "Invalid Request");
        return ResponseEntity.badRequest().body(errorResponse);
    }


}

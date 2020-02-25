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
        FieldError fieldError = e.getErrors().get(0);
        String errorMessage = fieldError.getField()+" "+fieldError.getDefaultMessage();
        RequestErrorResponse errorResponse = new RequestErrorResponse(fieldError, errorMessage);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}

package com.heavendeeds.heavendeeds.exception;

import com.heavendeeds.heavendeeds.HeavenApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;
import java.util.List;
@ControllerAdvice
public class HeavenApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private HeavenApiProperties forumApiProperties;

    @ExceptionHandler(HeavenApiResourceNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(HeavenApiResourceNotFoundException exception, WebRequest request){
        return handleExceptionInternal(exception,null,new HttpHeaders(), HttpStatus.NOT_FOUND,request);
    }

    @ExceptionHandler(HeavenApiInvalidInputException.class)
    protected ResponseEntity<Object> handleInvalidInputException(HeavenApiInvalidInputException bibApiException, WebRequest request){
        return handleExceptionInternal(bibApiException,this.exceptionResponse(bibApiException),new HttpHeaders(), HttpStatus.BAD_REQUEST,request);
    }

    @ExceptionHandler(HeavenApiException.class)
    protected ResponseEntity<Object> handle(HeavenApiException exception, WebRequest request){
        return handleExceptionInternal(exception,this.exceptionResponse(exception),new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,request);
    }

    private List<HeavenErrors> exceptionResponse(HeavenApiException exception){
        exception.getErrorList().forEach(formError -> {
            formError.setErrorCode(MessageFormat.format(forumApiProperties.getErrorMessages().get(formError.getErrorCode()),formError.getParameters()));
        });
        return exception.getErrorList();
    }
}

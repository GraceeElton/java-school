package com.lambdaschool.schools.Handlers;


import com.lambdaschool.schools.Excepions.ResourceFoundException;
import com.lambdaschool.schools.Excepions.ResourceNotFoundException;
import com.lambdaschool.schools.models.ErrorDetails;
import com.lambdaschool.schools.services.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{

    @Autowired
    private HelperFunctions helperFunctions;

    public RestExceptionHandler()
    {
        super();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnte)
    {
        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("ResourceNotFound");
        errorDetail.setDetail(rnte.getMessage());
        errorDetail.setDeveloperMessage(rnte.getClass().getName());


        errorDetail.setError(helperFunctions.getConstraintViolation(rnte));


        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceFoundException.class)
    public ResponseEntity<?> handleResourceFoundException(ResourceFoundException rfe)
    {
        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Resource Found");
        errorDetail.setDetail(rfe.getMessage());
        errorDetail.setDeveloperMessage(rfe.getClass().getName());


        errorDetail.setError(helperFunctions.getConstraintViolation(rfe));


        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }




    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request)
    {

        ErrorDetails errorDetail = new ErrorDetails();

        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Rest Internal Exception");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass().getName());


        errorDetail.setError(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail, null, status);
    }
}

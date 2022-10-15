package com.library.exception;

import com.library.exception.message.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class LibraryExceptionHandler extends ResponseEntityExceptionHandler {


        private ResponseEntity<Object> buildResponseEntity(ApiResponseError error) {

            return new ResponseEntity<>(error,error.getStatus());

        }

        @ExceptionHandler
    protected ResponseEntity<Object> handlerResourceNotFoundException (ResourceNotFoundException ex, WebRequest request){

            ApiResponseError error = new ApiResponseError(
                    HttpStatus.NOT_FOUND, ex.getMessage(),request.getDescription(false));

            return buildResponseEntity(error);

    }

    protected ResponseEntity<Object> handlerConflictException (ConflictException ex, WebRequest request){
            ApiResponseError error= new ApiResponseError(HttpStatus.CONFLICT, ex.getMessage(),request.getDescription(false));
            return buildResponseEntity(error);
    }

    protected ResponseEntity<Object> handlerBadRequestException (BadRequestException ex,WebRequest request){
            ApiResponseError error= new ApiResponseError(HttpStatus.BAD_REQUEST,ex.getMessage(),request.getDescription(false));
            return buildResponseEntity(error);
    }

    protected ResponseEntity<Object> handlerImageFileException (ImageFileException ex, WebRequest request){

            ApiResponseError error= new ApiResponseError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
            return buildResponseEntity(error);
    }

    }



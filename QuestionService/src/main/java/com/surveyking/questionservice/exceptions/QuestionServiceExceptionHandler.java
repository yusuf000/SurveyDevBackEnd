package com.surveyking.questionservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuestionServiceExceptionHandler  {


    @ExceptionHandler({InvalidExpressionException.class})
    public ResponseEntity<InvalidExpression> handleInvalidExpressionException(
            InvalidExpressionException invalidExpressionException
    ){
        InvalidExpression invalidExpression = new InvalidExpression(
                invalidExpressionException.getMessage(),
                invalidExpressionException.getCause(),
                HttpStatus.NOT_ACCEPTABLE
        );
        return new ResponseEntity<>(
                invalidExpression, HttpStatus.NOT_ACCEPTABLE
        );
    }

}

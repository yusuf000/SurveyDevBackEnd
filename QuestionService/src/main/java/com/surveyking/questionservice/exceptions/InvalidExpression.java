package com.surveyking.questionservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class InvalidExpression {
    String message;
    Throwable cause;
    HttpStatus httpStatus;
}

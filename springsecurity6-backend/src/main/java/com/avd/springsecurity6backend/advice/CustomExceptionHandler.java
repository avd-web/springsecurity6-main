package com.avd.springsecurity6backend.advice;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class CustomExceptionHandler {

    // Bad Credential : Authentication Failure : 401
    // Access denied : Authorization ERROR : 403
    // Invalid JWT Signature : 403
    // Token Expired

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex){

        ProblemDetail errorDetail = null;

          if (ex instanceof BadCredentialsException){
              errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
              errorDetail.setProperty("access_denied_reason", "Authentication Failure");
          }

          if (ex instanceof AccessDeniedException){
              errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
              errorDetail.setProperty("access_denied_reason", "not-authorized");
          }
    return errorDetail;
    }
}

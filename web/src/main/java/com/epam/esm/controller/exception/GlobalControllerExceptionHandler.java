package com.epam.esm.controller.exception;

import com.epam.esm.exception.AuthDateException;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.ModelException;
import com.epam.esm.exception.NoSuchEntityException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.auth.message.AuthException;
import java.util.Locale;

@RestControllerAdvice()
public class GlobalControllerExceptionHandler {

    @Autowired
    private ReloadableResourceBundleMessageSource bundleMessageSource;

    public GlobalControllerExceptionHandler(ReloadableResourceBundleMessageSource bundleMessageSource) {
        this.bundleMessageSource = bundleMessageSource;
    }

    private ResponseEntity<ModelException> buildResponse(String info, int code,
                                                         HttpStatus status) {
        ModelException response = new ModelException(code, info);
        return new ResponseEntity<>(response, status);
    }

    private String resolverBundle(String key, Locale locale) {
        return bundleMessageSource.getMessage(key, null, locale);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<ModelException> handlerExceptionNotFound
            (NoSuchEntityException exception, Locale locale) {
        return buildResponse(resolverBundle(exception.getMessage(),
                locale), 404666, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AuthDateException.class)
    public ResponseEntity<ModelException> handlerExceptionBadDate
            (AuthDateException exception, Locale locale) {
        return buildResponse(resolverBundle(exception.getMessage(),
                locale), 403666, HttpStatus.FORBIDDEN);

    }
    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<ModelException> handlerExceptionBadRequest
            (IncorrectDataException exception, Locale locale) {
        return buildResponse(resolverBundle(exception.getMessage(),
                locale), 400333, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ModelException> handlerException
            (Exception exception, Locale locale) {
        return buildResponse(resolverBundle(exception.getMessage(),
                locale), 599999, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ModelException> handlerException
            (MissingServletRequestParameterException e) {
        return buildResponse(e.getMessage(), 400222,
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ModelException> handlerException
            (TypeMismatchException e) {
        return buildResponse(e.getMessage(), 400000,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ModelException> handlerException(
            HttpRequestMethodNotSupportedException e) {
        return buildResponse(e.getMessage(), 405504,
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ModelException> handlerException(Locale locale) {
        final String message = "message.required.request";
        return buildResponse(resolverBundle(message, locale),
                400004, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ModelException> handlerException
            (NoHandlerFoundException e) {
        return buildResponse(e.getMessage(), 404444,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ModelException> handlerException(Exception e) {
        return buildResponse(e.getMessage(), 500005,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

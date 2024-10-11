package com.intuit.demo.converter.controller;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.intuit.demo.converter.dto.Error;
import java.util.concurrent.CompletionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Pruthvi Muddapuram
 */

@Slf4j
@Order
@ControllerAdvice
public class ConverterControllerAdvice {
  
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public Error handleRequestBodyValidationException(
      HttpMessageNotReadableException exception) {
    return new Error(exception.getMessage());
  }
  
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public Error handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
    return new Error(illegalArgumentException.getMessage());
  }
  
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public Error handleMissingRequestHeaderException(
      HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
    Error serviceErrorResponse = new Error();
    serviceErrorResponse.setMessage(httpRequestMethodNotSupportedException.getMessage());
    return serviceErrorResponse;
  }
  
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public Error handleMissingServletRequestException(
      MissingServletRequestParameterException missingServletRequestParameterException) {
    Error serviceErrorResponse = new Error();
    serviceErrorResponse.setMessage(missingServletRequestParameterException.getMessage());
    return serviceErrorResponse;
  }
  
  
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  @ExceptionHandler({CompletionException.class, Throwable.class})
  public Error handleAll(Throwable ex) {
    var message = ofNullable(ex);
    Error serviceErrorResponse = new Error();
    serviceErrorResponse.setMessage(String.valueOf(message));
    return serviceErrorResponse;
  }
}

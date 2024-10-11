package com.intuit.demo.converter.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Pruthvi Muddapuram
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
  
  public static final String API = "/api";
  public static final String CONVERT = "/convert";
  
  public static final String MARKDOWN_TO_HTML = "markdownTohtml";
  
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class SwaggerHttpStatusResponseCodes {
    
    public static final String OK = "200";
    public static final String BAD_REQUEST = "400";
    public static final String INTERNAL_SERVER_ERROR = "500";
  }
}

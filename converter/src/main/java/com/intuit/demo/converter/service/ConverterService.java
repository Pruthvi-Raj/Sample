package com.intuit.demo.converter.service;

/**
 * @author Pruthvi Muddapuram
 */

public interface ConverterService {
  
  String convertFormat(String toFormat, String fromFormat, String content);
  
}

package com.intuit.demo.converter.service;

import com.intuit.demo.converter.utils.Format;

/**
 * @author Pruthvi Muddapuram
 */

public interface ConverterService {
  
  String convertFormat(Format toFormat, Format fromFormat, String content);
  
}

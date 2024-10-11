package com.intuit.demo.converter.service.impl;

import com.intuit.demo.converter.service.ConverterService;
import org.springframework.stereotype.Service;

/**
 * @author Pruthvi Muddapuram
 */

@Service
public class ConverterServiceImpl implements ConverterService {
  
  /**
   * @param toFormat
   * @param fromFormat
   * @param content
   * @return String
   */
  @Override
  public String convertFormat(String toFormat, String fromFormat, String content) {
    return "";
  }
}

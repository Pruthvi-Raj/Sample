package com.intuit.demo.converter.controller;

import com.intuit.demo.converter.service.ConverterService;
import com.intuit.demo.converter.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pruthvi Muddapuram
 */


@RestController
@RequestMapping(Constants.API)
@RequiredArgsConstructor
public class ConverterController {
  
  private final ConverterService converterService;
  
  /** API to convert one format of data to another format.
   * As part of the initial release we will support
   * Markdown to HTML*/
  @PostMapping(Constants.CONVERT)
  public String convertMarkdownToHtml(
//      Adding request params to future-proof the API
      @RequestParam String toFormat,
      @RequestParam String fromFormat,
      @RequestBody String markdown) {
    return converterService.convertFormat(toFormat, fromFormat, markdown);
  }
  
}

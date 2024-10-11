package com.intuit.demo.converter.controller;

import com.intuit.demo.converter.utils.Constants;
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
public class ConverterController {
  
  /* Adding request params to future-proof the API  * */
  @PostMapping(Constants.CONVERT)
  public String convertMarkdownToHtml(
      @RequestParam String from,
      @RequestParam String to,
      @RequestBody String markdown) {
    return null;
  }
  
}

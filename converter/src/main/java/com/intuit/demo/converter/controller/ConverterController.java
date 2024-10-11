package com.intuit.demo.converter.controller;

import com.intuit.demo.converter.service.ConverterService;
import com.intuit.demo.converter.utils.Constants;
import com.intuit.demo.converter.utils.Format;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pruthvi Muddapuram
 */

@Slf4j
@RestController
@RequestMapping(Constants.API)
@RequiredArgsConstructor
public class ConverterController {
  
  private final ConverterService converterService;
  
  /**
   * API to convert one format of data to another format. As part of the initial release we will
   * support Markdown to HTML
   */
  @PostMapping(Constants.CONVERT)
  @Operation(description = "Convert markdown to html",
      responses = {
          @ApiResponse(
              responseCode = Constants.SwaggerHttpStatusResponseCodes.OK,
              content = @Content(schema = @Schema(implementation = String.class))),
          @ApiResponse(
              responseCode = Constants.SwaggerHttpStatusResponseCodes.BAD_REQUEST,
              description = "Bad request",
              content = @Content(schema = @Schema(implementation = Error.class))),
          @ApiResponse(
              responseCode = Constants.SwaggerHttpStatusResponseCodes.INTERNAL_SERVER_ERROR,
              description = "Internal server error",
              content = @Content(schema = @Schema(implementation = Error.class)))
      })
  public String convertMarkdownToHtml(
//      Adding request params to future-proof the API
      @RequestParam String toFormat,
      @RequestParam String fromFormat,
      @RequestBody String markdown) {
    Format toFormatEnum = Format.valueOf(toFormat.toUpperCase());
    Format fromFormatEnum = Format.valueOf(fromFormat.toUpperCase());
    log.info("Converting from {} to {}", fromFormatEnum, toFormatEnum);
    return converterService.convertFormat(toFormatEnum, fromFormatEnum, markdown);
  }
  
}

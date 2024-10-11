package com.intuit.demo.converter.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.intuit.demo.converter.service.ConverterService;
import com.intuit.demo.converter.utils.Constants;
import com.intuit.demo.converter.utils.Format;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Pruthvi Muddapuram
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(ConverterController.class)
class ConverterControllerTest {
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private ConverterService converterService;
  
  @BeforeEach
  void setUp() {
    when(converterService.convertFormat(Format.HTML, Format.MARKDOWN, "sample markdown"))
        .thenReturn("sample html");
  }
  
  @Test
  void testConvertMarkdownToHtml() throws Exception {
    mockMvc.perform(post(Constants.API + Constants.CONVERT)
            .param("toFormat", "html")
            .param("fromFormat", "markdown")
            .content("sample markdown")
            .contentType("text/plain"))
        .andExpect(status().isOk())
        .andExpect(content().string("sample html"));
  }
  
  
  @Test
  void testConvertMarkdownToHtml_BadRequest_EmptyRequest() throws Exception {
    mockMvc.perform(post(Constants.API + Constants.CONVERT)
            .param("toFormat", "html")
            .param("fromFormat", "markdown")
            .content("")
            .contentType("text/plain"))
        .andExpect(status().isBadRequest());
  }
  
  @Test
  void testConvertMarkdownToHtml_BadRequest_NullRequest() throws Exception {
    mockMvc.perform(post(Constants.API + Constants.CONVERT)
            .param("toFormat", "html")
            .param("fromFormat", "markdown")
            .contentType("text/plain"))
        .andExpect(status().isBadRequest());
  }
  
  @Test
  void testConvertMarkdownToHtml_BadRequest_NoToFormat() throws Exception {
    mockMvc.perform(post(Constants.API + Constants.CONVERT)
            .param("fromFormat", "markdown")
            .content("sample markdown")
            .contentType("text/plain"))
        .andExpect(status().isBadRequest());
  }
  
  @Test
  void testConvertMarkdownToHtml_BadRequest_NoFromFormat() throws Exception {
    mockMvc.perform(post(Constants.API + Constants.CONVERT)
            .param("toFormat", "html")
            .content("sample markdown")
            .contentType("text/plain"))
        .andExpect(status().isBadRequest());
  }
  
}

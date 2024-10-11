package com.intuit.demo.converter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.intuit.demo.converter.service.impl.ConverterServiceImpl;
import com.intuit.demo.converter.utils.Format;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author Pruthvi Muddapuram
 */

class ConverterServiceImplTest {
  
  Format fromFormat = Format.MARKDOWN;
  Format toFormat = Format.HTML;
  private ConverterServiceImpl converterService;
  
  static Stream<Arguments> markdownToHtmlProvider() {
    return Stream.of(
        arguments(null, "Error converting markdown to html"),
        arguments("", ""),
        arguments("$***", "<p>$***</p>\n"),
        arguments(" #$***", "<h1>$***</h1>\n"),
        arguments("## $***", "<h2>$***</h2>\n"),
        arguments("# # $***", "<h1># $***</h1>\n"),
        arguments("######## Heading with more than 6\n #Heading\n [link](http://example.com)",
            "<p>######## Heading with more than 6</p>\n<h1>Heading</h1>\n<p><a href=\"http://example.com\">link</a></p>\n"),
        arguments("# Heading\nThis is a [link](http://example.com)",
            "<h1>Heading</h1>\n<p>This is a <a href=\"http://example.com\">link</a></p>\n"),
        arguments("# Heading\nThis is a [link](http://example.com) wrapped in an unformatted line",
            "<h1>Heading</h1>\n<p>This is a <a href=\"http://example.com\">link</a> wrapped in an unformatted line</p>\n"),
        arguments("# Heading\n\nThis is a [link](http://example.com)",
            "<h1>Heading</h1>\n<p>This is a <a href=\"http://example.com\">link</a></p>\n"),
        arguments("# Heading\n [link](http://example.com)",
            "<h1>Heading</h1>\n<p><a href=\"http://example.com\">link</a></p>\n"),
        arguments("# Heading\n ####Sub-Heading\n [link](http://example.com)",
            "<h1>Heading</h1>\n<h4>Sub-Heading</h4>\n<p><a href=\"http://example.com\">link</a></p>\n"),
        arguments(
            "# Heading\n ####Sub-Heading\n [link](http://example.com)\n\n[link2](http://example2.com)",
            "<h1>Heading</h1>\n<h4>Sub-Heading</h4>\n<p><a href=\"http://example.com\">link</a></p>\n<p><a href=\"http://example2.com\">link2</a></p>\n"),
        arguments(
            "# Heading\n ####Sub-Heading\n [link](http://example.com)[link2](http://example2.com)",
            "<h1>Heading</h1>\n<h4>Sub-Heading</h4>\n<p><a href=\"http://example.com\">link</a><a href=\"http://example2.com\">link2</a></p>\n"),
        arguments("This is a [link](http://example.com) wrapped in a line\n ##Followed by header", "<p>This is a <a href=\"http://example.com\">link</a> wrapped in a line</p>\n<h2>Followed by header</h2>\n")
    );
  }
  
  @BeforeEach
  public void setUp() {
    converterService = new ConverterServiceImpl();
  }
  
  @ParameterizedTest
  @MethodSource("markdownToHtmlProvider")
  void testConvertMarkdownToHtml(String markdownContent, String expectedHtml) {
    String actualHtml = converterService.convertFormat(toFormat, fromFormat, markdownContent);
    assertEquals(expectedHtml, actualHtml);
  }
}

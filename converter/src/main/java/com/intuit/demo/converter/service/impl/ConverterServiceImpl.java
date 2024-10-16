package com.intuit.demo.converter.service.impl;

import static com.intuit.demo.converter.utils.Constants.MARKDOWN_TO_HTML;

import com.intuit.demo.converter.service.ConverterService;
import com.intuit.demo.converter.utils.Format;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Pruthvi Muddapuram
 */

@Slf4j
@Service
public class ConverterServiceImpl implements ConverterService {
  
  /**
   * @param toFormat
   * @param fromFormat
   * @param content
   * @return String
   */
  @Override
  public String convertFormat(Format toFormat, Format fromFormat, String content) {
    
    String type = fromFormat.getFormat() + "To" + toFormat.getFormat();
    
    if (type.equals(MARKDOWN_TO_HTML)) {
      return convertMarkdownToHtml(content);
    }
//    Add more cases here for future formats using switch case
    return "";
  }
  
  private String convertMarkdownToHtml(String markdown) {
    try {
      StringBuilder html = new StringBuilder();
      String[] markdownLines = markdown.split("\n");
      
      for (String line : markdownLines) {
        if (line.isBlank()) {
          continue;
        }
        if (isHeading(line)) {
          log.info("Converting heading");
          html.append(convertHeading(line)).append("\n");
        } else {
          log.info("Converting line with links");
          html.append(convertLineWithMultipleLinks(line)).append("\n");
        }
      }
      
      return html.toString();
    } catch (Exception e) {
      return "Error converting markdown to html";
    }
  }
  
  private boolean isHeading(String line) {
    return line.matches("^#{1,6}.*");
  }
  
  private String convertHeading(String line) {
    int headingLevel = 0;
    while (line.charAt(headingLevel) == '#') {
      headingLevel++;
    }
//    Assuming we support only 6 levels of headings and anything above 6 is an Unformatted line
    if (headingLevel > 6) {
      log.info("Heading level > than 6. Marking it as a <p>. Heading level: {}", headingLevel);
      return String.format("<p>%s</p>", line);
    }
    String content = line.substring(headingLevel).trim();
    return String.format("<h%d>%s</h%d>", headingLevel, content, headingLevel);
  }
  
  private String convertLineWithMultipleLinks(String line) {
    Pattern pattern = Pattern.compile("\\[(.*?)\\]\\((.*?)\\)");
    Matcher matcher = pattern.matcher(line);
    StringBuilder lineHtml = new StringBuilder();
    lineHtml.append("<p>");
    
    int lastIndex = 0;
    while (matcher.find()) {
//      Append the text before the link and linkText match
      lineHtml.append(line, lastIndex, matcher.start());
//      Capture link and linkText from the line
      String linkText = matcher.group(1);
      String link = matcher.group(2);
      lineHtml.append("<a href=\"").append(link).append("\">").append(linkText).append("</a>");
//      Moving the index to the end of the link to continue the search for the next link in the line
      lastIndex = matcher.end();
    }
    lineHtml.append(line, lastIndex, line.length()).append("</p>");
    return lineHtml.toString();
  }
}

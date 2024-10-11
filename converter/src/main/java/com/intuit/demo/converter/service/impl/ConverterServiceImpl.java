package com.intuit.demo.converter.service.impl;

import static com.intuit.demo.converter.utils.Constants.MARKDOWN_TO_HTML;

import com.intuit.demo.converter.service.ConverterService;
import com.intuit.demo.converter.utils.Format;
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
        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }
        if (isHeading(line)) {
          html.append(convertHeading(line));
        } else {
          html.append(convertLineWithLinks(line));
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
    String content = line.substring(headingLevel).trim();
    return String.format("<h%d>%s</h%d>", headingLevel, content, headingLevel);
  }
  
  private String convertLineWithLinks(String line) {
    StringBuilder lineHtml = new StringBuilder();
    int index = 0;
    
    while (index < line.length()) {
      if (line.charAt(index) == '[') {
        int endText = line.indexOf(']', index);
        int startLink = line.indexOf('(', endText);
        int endLink = line.indexOf(')', startLink);
        
        if (endText > 0 && startLink > endText && endLink > startLink) {
          String linkText = line.substring(index + 1, endText);
          String link = line.substring(startLink + 1, endLink);
          
          lineHtml.append("<a href=\"").append(link).append("\">").append(linkText).append("</a>");
          index = endLink + 1;
        } else {
          lineHtml.append(line.charAt(index));
          index++;
        }
      } else {
        lineHtml.append(line.charAt(index));
        index++;
      }
    }
//    Wrap the line in a paragraph tag
    return "<p>" + lineHtml + "</p>";
  }
}

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
        if (line.isBlank()) {
          continue;
        }
        line = line.trim();
        if (line.matches("^#{1,6}.*")) {
          int count = 0;
          while (line.charAt(count) == '#') {
            count++;
          }
          html.append("<h").append(count).append(">").append(line.substring(count).trim())
              .append("</h").append(count).append(">");
        }
//      [Link text](https://www.example.com)	<a href="https://www.example.com">Link text</a>
        else if (line.matches(".*\\[.*\\]\\(.*\\).*")) {
          int start = line.indexOf("[");
          int end = line.indexOf("]");
          String linkText = line.substring(start + 1, end);
          start = line.indexOf("(");
          end = line.indexOf(")");
          String link = line.substring(start + 1, end);
          html.append("<a href=\"").append(link).append("\">").append(linkText).append("</a>");
        } else {
          html.append("<p>").append(line).append("</p>");
        }
      }
      return html.toString();
    } catch (Exception e) {
      return "Error converting markdown to html";
    }
  }
  
}

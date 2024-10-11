package com.intuit.demo.converter.utils;

/**
 * @author Pruthvi Muddapuram
 */

public enum Format {
  MARKDOWN("markdown"),
  HTML("html");

  private String format;

  Format(String format) {
    this.format = format;
  }

  public String getFormat() {
    return format;
  }

  public static Format getFormat(String format) {
    for (Format f : Format.values()) {
      if (f.getFormat().equalsIgnoreCase(format)) {
        return f;
      }
    }
    return null;
  }
}

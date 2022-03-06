package br.com.nagata.dev.controller.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class URL {
  
  public static String decodeParam(String string) {
    try {
      return URLDecoder.decode(string, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      return "";
    }
  }

  public static List<Integer> decodeIntList(String string) {
    return Stream.of(string.split(","))
        .map(numero -> Integer.parseInt(numero))
        .collect(Collectors.toList());
  }
}

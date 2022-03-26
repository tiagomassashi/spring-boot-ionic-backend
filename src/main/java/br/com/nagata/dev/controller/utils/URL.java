package br.com.nagata.dev.controller.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class URL {

  public static String decodeParam(String string) {
    return URLDecoder.decode(string, StandardCharsets.UTF_8);
  }

  public static List<Integer> decodeIntList(String string) {
    return Stream.of(string.split(",")).map(Integer::parseInt).collect(Collectors.toList());
  }
}

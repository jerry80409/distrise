package com.github.jerry80409;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jerry80409.nostr.event.Tools;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) {
    final ObjectMapper jsonMapper = Tools.JSON_MAPPER;
  }
}
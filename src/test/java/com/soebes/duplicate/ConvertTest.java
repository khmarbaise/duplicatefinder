package com.soebes.duplicate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConvertTest {
  
  @Test
  void should_result() {
    byte[] bytes = { 0x10, 0x20, (byte) 0xA0, (byte) 0xff};
    String hex = Convert.toHex(bytes);
    assertThat(hex).isEqualTo("1020A0FF");
  }
}
package com.soebes.duplicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

class CalculateChecksumTest {
  
  private CalcuateChecksum calcuateChecksum;
  
  @BeforeEach
  void beforeEach() throws NoSuchAlgorithmException {
    calcuateChecksum = new CalcuateChecksum();
  }
  
  @Test
  void first_test() throws IOException {
    
    InputStream resourceAsStream = this.getClass().getResourceAsStream("/defined-content.file");
    
    //File file = new File("/Users/khmarbaise/Bilder/20190824_210637.mp4");
    //File file = new File("/Users/khmarbaise/Bilder/20200216_221735.jpg");
    ChecksumResult checksumResult = calcuateChecksum.forFile(resourceAsStream);
    
    assertThat(checksumResult).satisfies(s -> {
      assertThat(s.getReadBytes()).isEqualTo(15);
      assertThat(s.getDigest()).containsExactly( //
        0xDD, 0xA1, 0x11, 0xAF, 0xE0, 0xB2, 0x27, 0x1C, //
        0xBA, 0xAA, 0x94, 0x0F, 0xA1, 0xA6, 0xB2, 0x27, //
        0xAD, 0x47, 0x98, 0x78, 0xDB, 0x7B, 0x13, 0x94, //
        0x4B, 0xCD, 0x2E, 0xC6, 0x94, 0x88, 0xE9, 0x3A, //
        0xC4, 0x45, 0x3A, 0x7F, 0xEC, 0xE3, 0x29, 0xAE, //
        0x04, 0x3D, 0xD2, 0x39, 0x05, 0x38, 0xE7, 0x54, //
        0x22, 0x9E, 0xC7, 0x68, 0x18, 0x02, 0x84, 0x3F, //
        0xBE, 0x15, 0x58, 0x42, 0xE0, 0x80, 0x49, 0xCF);
    });
  }
  
}

package com.soebes.duplicate;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

class CalculateChecksumTest {
  
  @Test
  void first_test() throws NoSuchAlgorithmException, IOException {
    CalcuateChecksum calcuateChecksum = new CalcuateChecksum();
  
    File file = new File("/Users/khmarbaise/Bilder/20190824_210637.mp4");
    //File file = new File("/Users/khmarbaise/Bilder/20200216_221735.jpg");
    ChecksumResult checksumResult = calcuateChecksum.forFile(file);
    
    
    System.out.println("readBytes = " + checksumResult.getReadBytes());
    System.out.println("mdbytes = " + toHex(checksumResult.getDigest()));
  }
  
  String toHex(byte[] mdbytes) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < mdbytes.length; i++) {
      sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
    
  }
}

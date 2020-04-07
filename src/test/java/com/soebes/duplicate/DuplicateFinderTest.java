package com.soebes.duplicate;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class DuplicateFinderTest {
  
  @Test
  void first_test() throws NoSuchAlgorithmException, IOException {
    MessageDigest md = MessageDigest.getInstance("SHA-512");
    File file = new File("/Users/khmarbaise/Bilder/20190824_210637.mp4");
    //File file = new File("/Users/khmarbaise/Bilder/20200216_221735.jpg");
    FileInputStream fis = new FileInputStream(file);
    byte[] dataBytes = new byte[64 * 1024];
    
    long readBytes = 0L;
    int nread = 0;
    while ((nread = fis.read(dataBytes)) != -1) {
      md.update(dataBytes, 0, nread);
      readBytes += nread;
    }
    
    byte[] mdbytes = md.digest();
    System.out.println("readBytes = " + readBytes);
    System.out.println("mdbytes = " + toHex(mdbytes));
  }
  
  String toHex(byte[] mdbytes) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < mdbytes.length; i++) {
      sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
    
  }
}

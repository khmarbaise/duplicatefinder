package com.soebes.duplicate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalcuateChecksum {
  
  private MessageDigest messageDigest;
  
  public CalcuateChecksum() throws NoSuchAlgorithmException {
    this.messageDigest = MessageDigest.getInstance("SHA-512");
  }
  
  public ChecksumResult forFile(File file) throws IOException {
    try (FileInputStream fis = new FileInputStream(file)) {
      byte[] dataBytes = new byte[64 * 1024];
      
      long readBytes = 0L;
      int nread = 0;
      while ((nread = fis.read(dataBytes)) != -1) {
        messageDigest.update(dataBytes, 0, nread);
        readBytes += nread;
      }
      return new ChecksumResult(messageDigest.digest(), file.getName(), readBytes);
    }
    
  }
  
}

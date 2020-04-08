package com.soebes.duplicate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalcuateChecksum {
  
  private static final int BUFFER_SIZE = 64 * 1024;
  
  private MessageDigest messageDigest;
  
  public CalcuateChecksum() throws NoSuchAlgorithmException {
    this.messageDigest = MessageDigest.getInstance("SHA-512");
  }
  
  public ChecksumResult forFile(File file) throws IOException {
    try (FileInputStream fis = new FileInputStream(file)) {
      return forFile(fis);
    }
    
  }
  public ChecksumResult forFile(InputStream inputStream) throws IOException {
      byte[] dataBytes = new byte[BUFFER_SIZE];
      
      long readBytes = 0L;
      int nread = 0;
      while ((nread = inputStream.read(dataBytes)) != -1) {
        messageDigest.update(dataBytes, 0, nread);
        readBytes += nread;
      }
      return new ChecksumResult(messageDigest.digest(), readBytes);
  }
  
}

package com.soebes.duplicate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class CalculateChecksum {

  private static final int BUFFER_SIZE = 64 * 1024;

  private final MessageDigest messageDigest;

  CalculateChecksum() throws NoSuchAlgorithmException {
    this.messageDigest = MessageDigest.getInstance("SHA-512");
  }

  ChecksumResult forFile(File file) throws IOException {
    try (var fis = new FileInputStream(file)) {
      return forFile(fis);
    }
  }

  ChecksumResult forFile(InputStream inputStream) throws IOException {
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

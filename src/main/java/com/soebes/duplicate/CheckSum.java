package com.soebes.duplicate;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class CheckSum {
  
  public static ChecksumForFileResult forFile(Path path) {
    try {
      ChecksumResult checksumResult = new CalcuateChecksum().forFile(path.toFile());
      return new ChecksumForFileResult(checksumResult.getDigest(), path.getFileName()
        .toString(), checksumResult.getReadBytes());
    } catch (IOException | NoSuchAlgorithmException e) {
      //Translate to RuntimeException.
      throw new RuntimeException(e.getClass().getName(), e);
    }
  }
  
}

package com.soebes.duplicate;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class ChecksumForFile {
  
  public ChecksumForFileResult forFile(Path path) throws NoSuchAlgorithmException, IOException {
    ChecksumResult checksumResult = new CalcuateChecksum().forFile(path.toFile());
    return new ChecksumForFileResult(checksumResult.getDigest(), path.getFileName()
      .toString(), checksumResult.getReadBytes());
  }
  
}

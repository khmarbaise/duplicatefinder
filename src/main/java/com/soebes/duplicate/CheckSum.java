package com.soebes.duplicate;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

record CheckSum() {

  static ChecksumForFileResult forFile(Path path) {
    try {
      ChecksumResult checksumResult = new CalculateChecksum().forFile(path.toFile());
      return new ChecksumForFileResult(checksumResult.digest(), path.getFileName()
          .toString(), checksumResult.readBytes());
    } catch (IOException | NoSuchAlgorithmException e) {
      //Translate to RuntimeException.
      throw new RuntimeException(e.getClass().getName(), e);
    }
  }

}

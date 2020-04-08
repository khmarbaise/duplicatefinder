package com.soebes.duplicate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Duplicatefinder {
  
  public static void main(String[] args) {
    
    try {
  
      List<Path> collect = Files.list(Paths.get(args[0]))
        .filter(Files::isRegularFile)
        .collect(Collectors.toList());
  
      for (Path file : collect) {
        ChecksumForFileResult checksumForFileResult = new ChecksumForFile().forFile(file);
        System.out.println("File: " + checksumForFileResult.getFileName());
        System.out.println("      Size: " + String.format(Locale.GERMANY, "%,d",
          checksumForFileResult.getReadBytes()) + " bytes.");
        System.out.println("   SHA-512: " + Convert.toHex(checksumForFileResult.getDigest()));
      }
  
    } catch (IOException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  
  
  }
}

package com.soebes.duplicate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Duplicatefinder {
  
  private static final Function<Path, ChecksumForFileResult> checksumForFile = p ->
    new CheckSum().forFile(p);
  
  public static void main(String[] args) {
    
    try {
      
      List<Path> fileCollection = Files.list(Paths.get(args[0]))
        .filter(Files::isRegularFile)
        .collect(Collectors.toList());
  
      List<ChecksumForFileResult> checkSumResults = fileCollection.stream()
        .map(checksumForFile)
        .collect(Collectors.toList());
      
      checkSumResults.stream().forEach(item -> {
        System.out.print(Convert.toHex(item.getDigest()));
        System.out.print(" (Size: " + String.format(Locale.GERMANY, "%,d",
          item
            .getReadBytes()) + " bytes.)");
        System.out.println(" " + item.getFileName());
      });
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

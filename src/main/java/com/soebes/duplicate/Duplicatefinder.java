package com.soebes.duplicate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
  
      List<ChecksumForFileResult> checkSumResults = fileCollection.parallelStream()
        .map(checksumForFile)
        .collect(Collectors.toList());
      
      checkSumResults.stream().forEach(item -> {
        System.out.print(Convert.toHex(item.getDigest()));
        System.out.print(" (" + String.format(Locale.GERMANY, "%,d",
          item
            .getReadBytes()) + " bytes.)");
        System.out.println(" " + item.getFileName());
      });
  
      Map<ByteArrayWrapper, List<ChecksumForFileResult>> collect = checkSumResults.stream()
        .collect(Collectors.groupingBy(s -> s.getDigest()))
        .entrySet()
        .stream()
        .filter(s -> s.getValue().size() > 1)
        .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
  
      System.out.println("Number of duplicates:" + collect.size());
      for (Map.Entry<ByteArrayWrapper, List<ChecksumForFileResult>> element : collect.entrySet()) {
        System.out.println("CheckSum: " + Convert.toHex(element.getKey()));
        
        for (ChecksumForFileResult item : element.getValue()) {
          System.out.print("  " + item.getFileName() + " (");
          System.out.println(" (" + String.format(Locale.GERMANY, "%,d", item.getReadBytes()) + " bytes.)");
        }
        
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

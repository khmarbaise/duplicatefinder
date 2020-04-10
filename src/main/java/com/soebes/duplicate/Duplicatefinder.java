package com.soebes.duplicate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Duplicatefinder {
  
  private static final Function<Path, ChecksumForFileResult> checksumForFile = CheckSum::forFile;
  
  private static String formatting(Long readBytes) {
    StringBuilder sb = new StringBuilder();
    sb.append(" (");
    
    sb.append(String.format(Locale.GERMANY, "%,d", readBytes));
    sb.append(" bytes.)");
    return sb.toString();
  }
  
  public static void main(String[] args) {
    
    try {
      
      List<Path> fileCollection = Files.list(Paths.get(args[0]))
        .filter(Files::isRegularFile)
        .collect(toList());
      
      List<ChecksumForFileResult> checkSumResults = fileCollection.parallelStream()
        .map(checksumForFile)
        .collect(toList());
  
      checkSumResults.stream().forEach(item -> {
        System.out.print(Convert.toHex(item.getDigest()));
        System.out.print(formatting(item.getReadBytes()));
        System.out.println(" " + item.getFileName());
      });
  
      Map<ByteArrayWrapper, List<ChecksumForFileResult>> collect = checkSumResults.stream()
        .collect(groupingBy(ChecksumForFileResult::getDigest))
        .entrySet()
        .stream()
        .filter(s -> s.getValue().size() > 1)
        .collect(toMap(k -> k.getKey(), v -> v.getValue()));
  
      System.out.println("Number of duplicates:" + collect.size());
  
      for (Map.Entry<ByteArrayWrapper, List<ChecksumForFileResult>> element : collect.entrySet()) {
        System.out.println("CheckSum: " + Convert.toHex(element.getKey()));
        for (ChecksumForFileResult item : element.getValue()) {
          System.out.print("  " + item.getFileName() + " (");
          System.out.println(formatting(item.getReadBytes()));
        }
      }

      long readBytesTotal = checkSumResults.stream().mapToLong(ChecksumForFileResult::getReadBytes).sum();
      System.out.println("readBytesTotal = " + formatting(readBytesTotal));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

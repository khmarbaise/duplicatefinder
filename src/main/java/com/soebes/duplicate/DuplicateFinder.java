package com.soebes.duplicate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

class DuplicateFinder {

  static Function<Path, ChecksumForFileResult> forFile = path -> {
    try {
      ChecksumResult checksumResult = new CalculateChecksum().forFile(path.toFile());
      return new ChecksumForFileResult(checksumResult.digest(), path.getFileName()
          .toString(), checksumResult.readBytes());
    } catch (IOException | NoSuchAlgorithmException e) {
      //Translate to RuntimeException.
      throw new RuntimeException(e.getClass().getName(), e);
    }
  };

  private static String formatting(Long readBytes) {
    return " (" + String.format(Locale.GERMANY, "%,d", readBytes) + " bytes.)";
  }

  public static void main(String[] args) {

    try {

      var fileCollection = Files.list(Paths.get(args[0])).filter(Files::isRegularFile).toList();

      var checkSumResults = fileCollection.parallelStream().map(forFile).toList();

      System.out.println("Total of found files:: " + checkSumResults.size());
      checkSumResults.forEach(item -> {
        System.out.print(Convert.toHex(item.digest()));
        System.out.print(formatting(item.readBytes()));
        System.out.println(" " + item.fileName());
      });

      var duplicateFiles = checkSumResults.stream()
          .collect(groupingBy(ChecksumForFileResult::digest))
          .entrySet()
          .stream()
          .filter(s -> s.getValue().size() > 1)
          .collect(toMap(Entry::getKey, Entry::getValue));

      System.out.println("Number of duplicates:" + duplicateFiles.size());

      for (var element : duplicateFiles.entrySet()) {
        System.out.println("CheckSum: " + Convert.toHex(element.getKey()));
        for (var item : element.getValue()) {
          System.out.print("  " + item.fileName() + " (");
          System.out.println(formatting(item.readBytes()));
        }
      }

      var readTotalBytes = checkSumResults.stream().mapToLong(ChecksumForFileResult::readBytes).sum();
      System.out.println("readTotalBytes = " + formatting(readTotalBytes));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

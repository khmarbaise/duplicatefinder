package com.soebes.duplicate;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DirectoryTreeTest {
  
  @Test
  void first() throws IOException {
    List<Path> collect = Files.list(Paths.get("/Users/khmarbaise/Bilder"))
      .filter(Files::isRegularFile)
      .collect(Collectors.toList());
  
    collect.stream().forEach(path -> System.out.println("path = " + path));
  }
}

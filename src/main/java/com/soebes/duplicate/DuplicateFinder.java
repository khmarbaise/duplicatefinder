package com.soebes.duplicate;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

import com.soebes.duplicate.CalculateChecksum.Result.Failure;
import com.soebes.duplicate.CalculateChecksum.Result.Success;

import static java.lang.System.out;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

class DuplicateFinder {

  sealed interface CheckSumResult<V> {
    record Success<V>(V result) implements CheckSumResult<V> { }
    record Failure<V>(Throwable cause) implements CheckSumResult<V> { }
  }

  static Function<Path, CheckSumResult<ChecksumForFileResult>> toChecksumForFile = path -> {
    try {
      return switch (new CalculateChecksum().forFile(path)) {
        case Failure(Throwable cause) -> new CheckSumResult.Failure<>(cause);
        case Success(ChecksumResult success) -> new CheckSumResult.Success<>(new ChecksumForFileResult(success.digest(), path, success.readBytes()));
      };
    } catch (NoSuchAlgorithmException e) {
      return new CheckSumResult.Failure<>(e);
    }
  };

  private static String formatting(Long readBytes) {
    return String.format(Locale.GERMANY, " ( %,d bytes.)", readBytes);
  }

  private static final Predicate<Path> IS_REGULAR_FILE = Files::isRegularFile;
  private static final Predicate<Path> IS_READABLE = Files::isReadable;
  private static final Predicate<Path> IS_VALID_FILE = IS_REGULAR_FILE.and(IS_READABLE);

  private static Collection<Path> selectAllFiles(Path start) throws IOException {
    try (var pathStream = Files.walk(start)) {
      return pathStream.filter(IS_VALID_FILE).toList();
    }
  }

  static void main(String[] args) throws IOException {
    Path searchPath = Paths.get(args[0]);
    var imageFiles = selectAllFiles(searchPath);
    var checkSumResults = imageFiles
        .parallelStream()
        .map(toChecksumForFile)
        .toList();

    out.println("Total of found files:: " + checkSumResults.size());
    var duplicateFiles = checkSumResults
        .stream()
        .filter(CheckSumResult.Success.class::isInstance)
        .map(s -> ((CheckSumResult.Success<ChecksumForFileResult>) s).result())
        .collect(groupingBy(ChecksumForFileResult::digest))
        .entrySet()
        .stream()
        .filter(duplicates)
        .collect(toMap(Entry::getKey, Entry::getValue));

    out.println("Number of duplicates:" + duplicateFiles.size());

    out.println("Duplicates within Path: " + searchPath);
    var reducibleSize = duplicateFiles
        .entrySet()
        .stream()
        .mapToLong(item -> {
          out.println("HASH: " + HexFormat.of().withUpperCase().formatHex(item.getKey().byteArray()));
          for (var entry : item.getValue()) {
            out.print("  " + searchPath.relativize(entry.fileName()));
            out.println(formatting(entry.readBytes()));
          }
          return item.getValue().getFirst().readBytes() * (item.getValue().size() - 1);
        }).sum();

    var totalNumberOfReadBytes = checkSumResults
        .stream()
        .filter(CheckSumResult.Success.class::isInstance)
        .map(s -> ((CheckSumResult.Success<ChecksumForFileResult>) s).result())
        .mapToLong(ChecksumForFileResult::readBytes).sum();
    out.println("totalNumberOfReadBytes = " + formatting(totalNumberOfReadBytes));
    out.println("reducibleSize = " + formatting(reducibleSize));

    var errorList = checkSumResults
        .stream()
        .filter(CheckSumResult.Failure.class::isInstance)
        .map(s -> ((CheckSumResult.Failure<ChecksumForFileResult>) s).cause())
        .distinct()
        .toList();
    if (!errorList.isEmpty()) {
      out.println("--------------- ERRORS ------------------------");
      errorList.forEach(out::println);
    }
  }

  static final Predicate<Entry<ByteArrayWrapper, List<ChecksumForFileResult>>> duplicates = s -> s.getValue().size() > 1;

}

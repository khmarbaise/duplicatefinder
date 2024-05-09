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

import com.soebes.duplicate.CalculateChecksum.Result.Failure;
import com.soebes.duplicate.CalculateChecksum.Result.Success;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class CalculateChecksum {

  private static final int BUFFER_SIZE = 64 * 1024;

  private final MessageDigest messageDigest;

  CalculateChecksum() throws NoSuchAlgorithmException {
    this.messageDigest = MessageDigest.getInstance("SHA-512");
  }

  sealed interface Result<V> {
    record Success<V>(V result) implements Result<V> { }
    record Failure<V>(Throwable cause) implements Result<V> { }
  }

  Result<ChecksumResult> forFile(Path file) {
    try (var fis = Files.newInputStream(file)) {
      return new Success<>(forFile(fis));
    } catch (Exception e) {
      return new Failure<>(e);
    }
  }

  ChecksumResult forFile(InputStream inputStream) throws IOException {
    var dataBytes = new byte[BUFFER_SIZE];

    var readBytes = 0L;
    int nread;
    while ((nread = inputStream.read(dataBytes)) != -1) {
      messageDigest.update(dataBytes, 0, nread);
      readBytes += nread;
    }
    return new ChecksumResult(messageDigest.digest(), readBytes);
  }

}

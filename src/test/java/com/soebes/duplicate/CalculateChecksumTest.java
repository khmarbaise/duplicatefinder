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
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculateChecksumTest {

  private CalculateChecksum calculateChecksum;

  @BeforeEach
  void beforeEach() throws NoSuchAlgorithmException {
    calculateChecksum = new CalculateChecksum();
  }

  @Test
  void defined_content_should_calculate_the_given_result() throws IOException {
    var inputStream = Files.newInputStream(Path.of("src/test/resources/defined-content.file"));
    var checksumResult = calculateChecksum.forFile(inputStream);
    assertThat(checksumResult).satisfies(s -> {
      assertThat(s.readBytes()).isEqualTo(15);
      assertThat(s.digest()).containsExactly( //
          0xDD, 0xA1, 0x11, 0xAF, 0xE0, 0xB2, 0x27, 0x1C, //
          0xBA, 0xAA, 0x94, 0x0F, 0xA1, 0xA6, 0xB2, 0x27, //
          0xAD, 0x47, 0x98, 0x78, 0xDB, 0x7B, 0x13, 0x94, //
          0x4B, 0xCD, 0x2E, 0xC6, 0x94, 0x88, 0xE9, 0x3A, //
          0xC4, 0x45, 0x3A, 0x7F, 0xEC, 0xE3, 0x29, 0xAE, //
          0x04, 0x3D, 0xD2, 0x39, 0x05, 0x38, 0xE7, 0x54, //
          0x22, 0x9E, 0xC7, 0x68, 0x18, 0x02, 0x84, 0x3F, //
          0xBE, 0x15, 0x58, 0x42, 0xE0, 0x80, 0x49, 0xCF);
    });
  }

}

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

import java.util.Arrays;

record ByteArrayWrapper(byte[] byteArray) {

  ByteArrayWrapper(byte[] byteArray) {
    this.byteArray = Arrays.copyOf(byteArray, byteArray.length);
  }


  @Override
  public boolean equals(Object o) {
    if (o instanceof ByteArrayWrapper(var that) ) {
      return Arrays.equals(byteArray, that);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(byteArray);
  }

  @Override
  public String toString() {
    return "ByteArrayWrapper{" +
        "byteArray=" + Arrays.toString(byteArray) +
        '}';
  }
}

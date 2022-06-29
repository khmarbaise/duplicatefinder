package com.soebes.duplicate;

import java.util.Arrays;
import java.util.Objects;

record ByteArrayWrapper(byte[] byteArray) {

  ByteArrayWrapper(byte[] byteArray) {
    this.byteArray = byteArray.clone();
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ByteArrayWrapper byteArrayWrapper
        && Arrays.equals(byteArrayWrapper.byteArray, byteArray);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Arrays.hashCode(byteArray));
  }
}

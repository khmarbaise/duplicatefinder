package com.soebes.duplicate;

import java.util.Arrays;

record ByteArrayWrapper(byte[] byteArray) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ByteArrayWrapper)) {
      return false;
    }
    ByteArrayWrapper that = (ByteArrayWrapper) o;
    return Arrays.equals(byteArray, that.byteArray);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(byteArray);
  }
}

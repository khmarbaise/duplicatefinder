package com.soebes.duplicate;

import java.util.Arrays;

record ByteArrayWrapper(byte[] byteArray) {

  ByteArrayWrapper(byte[] byteArray) {
    this.byteArray = Arrays.copyOf(byteArray, byteArray.length);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ByteArrayWrapper that)) return false;
    return Arrays.equals(byteArray, that.byteArray);
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

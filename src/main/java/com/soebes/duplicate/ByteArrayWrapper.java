package com.soebes.duplicate;

import java.util.Arrays;

public class ByteArrayWrapper {
  
  private final byte[] byteArray;
  
  public ByteArrayWrapper(byte[] byteArray) {
    this.byteArray = byteArray;
  }
  
  public byte[] getByteArray() {
    return byteArray;
  }
  
  @Override
  public final boolean equals(Object o) {
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
  public final int hashCode() {
    return Arrays.hashCode(byteArray);
  }
}

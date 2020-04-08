package com.soebes.duplicate;

public final class Convert {
  private Convert() {
    // intentionally empty.
  }

  public static String toHex(byte[] mdbytes) {
    StringBuilder sb = new StringBuilder(mdbytes.length * 2);
    for (byte mdbyte : mdbytes) {
      sb.append(String.format("%02X", mdbyte));
    }
    return sb.toString();
  }
  
}

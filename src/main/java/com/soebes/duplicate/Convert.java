package com.soebes.duplicate;

final class Convert {
  private Convert() {
    // intentionally empty.
  }

  static String toHex(byte[] mdbytes) {
    StringBuilder sb = new StringBuilder(mdbytes.length * 2);
    for (byte mdbyte : mdbytes) {
      sb.append(String.format("%02X", mdbyte));
    }
    return sb.toString();
  }

  static String toHex(ByteArrayWrapper mdbytes) {
    StringBuilder sb = new StringBuilder(mdbytes.byteArray().length * 2);
    for (byte mdbyte : mdbytes.byteArray()) {
      sb.append(String.format("%02X", mdbyte));
    }
    return sb.toString();
  }

}

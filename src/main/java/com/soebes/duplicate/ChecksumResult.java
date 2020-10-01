package com.soebes.duplicate;

final class ChecksumResult {
  private final byte[] digest;
  private final Long readBytes;

  ChecksumResult(byte[] digest, Long readBytes) {
    this.digest = digest;
    this.readBytes = readBytes;
  }

  byte[] getDigest() {
    return digest;
  }

  Long getReadBytes() {
    return readBytes;
  }
}

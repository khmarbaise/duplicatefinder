package com.soebes.duplicate;

final class ChecksumForFileResult {
  private final ByteArrayWrapper digest;
  private final String fileName;
  private final Long readBytes;

  ChecksumForFileResult(byte[] digest, String fileName, Long readBytes) {
    this.digest = new ByteArrayWrapper(digest);
    this.fileName = fileName;
    this.readBytes = readBytes;
  }

  ByteArrayWrapper getDigest() {
    return digest;
  }

  String getFileName() {
    return fileName;
  }

  Long getReadBytes() {
    return readBytes;
  }
}

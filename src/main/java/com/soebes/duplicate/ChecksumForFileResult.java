package com.soebes.duplicate;

record ChecksumForFileResult(ByteArrayWrapper digest, String fileName, Long readBytes) {

  ChecksumForFileResult(byte[] digest, String fileName, Long readBytes) {
    this(new ByteArrayWrapper(digest), fileName, readBytes);
  }

}

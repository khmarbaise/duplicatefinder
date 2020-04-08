package com.soebes.duplicate;

public class ChecksumForFileResult {
  private ByteArrayWrapper digest;
  private String fileName;
  private Long readBytes;
  
  public ChecksumForFileResult(byte[] digest, String fileName, Long readBytes) {
    this.digest = new ByteArrayWrapper(digest);
    this.fileName = fileName;
    this.readBytes = readBytes;
  }
  
  public ByteArrayWrapper getDigest() {
    return digest;
  }
  
  public String getFileName() {
    return fileName;
  }
  
  public Long getReadBytes() {
    return readBytes;
  }
}

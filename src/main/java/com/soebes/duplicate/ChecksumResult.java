package com.soebes.duplicate;

public class ChecksumResult {
  private byte[] digest;
  private String fileName;
  private Long readBytes;
  
  public ChecksumResult(byte[] digest, Long readBytes) {
    this.digest = digest;
    this.readBytes = readBytes;
  }
  
  public byte[] getDigest() {
    return digest;
  }
  
  public Long getReadBytes() {
    return readBytes;
  }
}

package com.soebes.duplicate;

public class ChecksumResult {
  private byte[] digest;
  private String fileName;
  private Long readBytes;
  
  public ChecksumResult(byte[] digest, String fileName, Long readBytes) {
    this.digest = digest;
    this.fileName = fileName;
    this.readBytes = readBytes;
  }
  
  public byte[] getDigest() {
    return digest;
  }
  
  public String getFileName() {
    return fileName;
  }
  
  public Long getReadBytes() {
    return readBytes;
  }
}

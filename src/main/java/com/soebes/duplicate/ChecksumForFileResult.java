package com.soebes.duplicate;

public class ChecksumForFileResult {
  private byte[] digest;
  private String fileName;
  private Long readBytes;
  
  public ChecksumForFileResult(byte[] digest, String fileName, Long readBytes) {
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

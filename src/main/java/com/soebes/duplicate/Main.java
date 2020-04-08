package com.soebes.duplicate;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class Main {
  
  public static void main(String[] args) {
    try {
      CalcuateChecksum calcuateChecksum = new CalcuateChecksum();
      for (String arg : args) {
        ChecksumResult checksumResult = calcuateChecksum.forFile(new File(arg));
        System.out.println("File: " + arg);
        System.out.println("      Size: " + String.format(Locale.GERMANY, "%,d",
          checksumResult.getReadBytes()) + " bytes.");
        System.out.println("   SHA-512: " + Convert.toHex(checksumResult.getDigest()));
      }
    } catch (NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
    }
  
  }
}

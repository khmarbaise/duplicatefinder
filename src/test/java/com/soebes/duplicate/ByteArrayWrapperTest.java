package com.soebes.duplicate;

import org.junit.jupiter.api.Test;

class ByteArrayWrapperTest {
  
  @Test
  void verifyHashCodeEquals() {
    byte[] first = { 0x10, 0x20, 0x30};
    ByteArrayWrapper firstBAM = new ByteArrayWrapper(first);
    ByteArrayWrapper secondBAM = new ByteArrayWrapper(first);
    
  }
}
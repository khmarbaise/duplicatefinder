package com.soebes.duplicate;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class ByteArrayWrapperTest {

  @Test
  void verify_hashcode_and_equals() {
    EqualsVerifier.forClass(ByteArrayWrapper.class).suppress(Warning.ZERO_FIELDS).verify();
  }
}
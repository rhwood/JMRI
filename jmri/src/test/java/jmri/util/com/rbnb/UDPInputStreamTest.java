// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.util.com.rbnb;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 * Tests for the UDPInputStream class
 *
 * @author Paul Bender Copyright (C) 2016
 */
public class UDPInputStreamTest  {

   @Test
   public void testCtor(){
      Assert.assertNotNull(new UDPInputStream());
   }

}

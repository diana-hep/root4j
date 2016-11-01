/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Thu May 10 12:10:41 PDT 2001
 */
package org.dianahep.root4j.interfaces;

public interface TLeafB extends org.dianahep.root4j.RootObject, TLeaf
{
   public final static int rootIOVersion = 1;
   public final static int rootCheckSum = 777773410;

   /** Maximum value if leaf range is specified */
   byte getMaximum();

   /** Minimum value if leaf range is specified */
   byte getMinimum();

   byte getValue(long index) throws java.io.IOException;
}

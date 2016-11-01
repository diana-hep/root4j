/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Thu May 10 12:10:41 PDT 2001
 */
package org.dianahep.root4j.interfaces;

public interface TLeafObject extends org.dianahep.root4j.RootObject, TLeaf
{
   public final static int rootIOVersion = 4;

   Object getValue(long index) throws java.io.IOException;

   /** Support for Virtuality */
   boolean getVirtual();
}

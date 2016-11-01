/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Fri Jan 12 13:42:24 PST 2001
 */
package org.dianahep.root4j.interfaces;

public interface TStreamerElement extends org.dianahep.root4j.RootObject, TNamed
{
   int getArrayDim();

   int getArrayLength();

   int[] getMaxIndex();

   int getSize();

   int getType();

   String getTypeName();
}

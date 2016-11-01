/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Sat May 05 18:29:49 PDT 2001
 */
package org.dianahep.root4j.interfaces;

public interface TCollection extends org.dianahep.root4j.RootObject, TObject
{
   public final static int rootIOVersion = 3;

   Object getElementAt(int index);

   int getLast();

   /** name of the collection */
   String getName();

   /** number of elements in collection */
   int getSize();
}

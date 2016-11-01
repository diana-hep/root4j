/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Wed Jan 10 15:21:38 PST 2001
 */
package org.dianahep.root4j.interfaces;

public interface TNamed extends org.dianahep.root4j.RootObject, TObject
{
   public final static int rootIOVersion = 1;

   /** object identifier */
   String getName();

   /** object title */
   String getTitle();
}

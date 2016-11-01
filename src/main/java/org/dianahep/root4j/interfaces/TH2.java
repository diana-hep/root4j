/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Mon Apr 21 13:58:49 PDT 2003
 */
package org.dianahep.root4j.interfaces;

public interface TH2 extends org.dianahep.root4j.RootObject, org.dianahep.root4j.interfaces.TH1
{
   public final static int rootIOVersion = 3;

   /** Scale factor */
   double getScalefactor();

   /** Total Sum of weight*X*Y */
   double getTsumwxy();

   /** Total Sum of weight*Y */
   double getTsumwy();

   /** Total Sum of weight*Y*Y */
   double getTsumwy2();
}

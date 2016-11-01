/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Mon Apr 21 13:58:49 PDT 2003
 */
package org.dianahep.root4j.interfaces;

public interface TAxis extends org.dianahep.root4j.RootObject, org.dianahep.root4j.interfaces.TNamed, org.dianahep.root4j.interfaces.TAttAxis
{
   public final static int rootIOVersion = 6;
   public final static int rootCheckSum = 18741940;

   /** first bin to display */
   int getFirst();

   /** last bin to display */
   int getLast();

   /** Number of bins */
   int getNbins();

   /** on/off displaying time values instead of numerics */
   boolean getTimeDisplay();

   /** Date&time format, ex: 09/12/99 12:34:00 */
   org.dianahep.root4j.interfaces.TString getTimeFormat();

   /** Bin edges array in X */
   org.dianahep.root4j.interfaces.TArrayD getXbins();

   /** upper edge of last bin */
   double getXmax();

   /** low edge of first bin */
   double getXmin();
   
}

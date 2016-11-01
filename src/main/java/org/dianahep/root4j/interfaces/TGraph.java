/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Mon Apr 21 13:58:49 PDT 2003
 */
package org.dianahep.root4j.interfaces;

public interface TGraph extends org.dianahep.root4j.RootObject, org.dianahep.root4j.interfaces.TNamed, org.dianahep.root4j.interfaces.TAttLine, org.dianahep.root4j.interfaces.TAttFill, org.dianahep.root4j.interfaces.TAttMarker
{
   public final static int rootIOVersion = 3;

   /** Pointer to list of functions (fits and user) */
   org.dianahep.root4j.interfaces.TList getFunctions();

   /** Pointer to histogram used for drawing axis */
   org.dianahep.root4j.interfaces.TH1F getHistogram();

   /** Maximum value for plotting along y */
   double getMaximum();

   /** Minimum value for plotting along y */
   double getMinimum();

   /** Number of points */
   int getNpoints();

   /** [fNpoints] array of X points */
   double[] getX();

   /** [fNpoints] array of Y points */
   double[] getY();
}

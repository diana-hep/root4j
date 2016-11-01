/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Mon Jan 15 18:53:43 PST 2001
 */
package org.dianahep.root4j.interfaces;

public interface TAttMarker extends org.dianahep.root4j.RootObject
{
   public final static int rootIOVersion = 1;

   /** Marker color index */
   short getMarkerColor();

   /** Marker size */
   float getMarkerSize();

   /** Marker style */
   short getMarkerStyle();
}

/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Wed Jan 10 15:19:15 PST 2001
 */
package org.dianahep.root4j.interfaces;

public interface TStreamerInfo extends org.dianahep.root4j.RootObject, TNamed
{
   int getCheckSum();

   int getClassVersion();

   org.dianahep.root4j.interfaces.TObjArray getElements();
}

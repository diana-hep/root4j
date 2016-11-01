/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Fri Jan 19 12:59:21 PST 2001
 */
package org.dianahep.root4j.interfaces;

public interface TBranchObject extends org.dianahep.root4j.RootObject, TBranch
{
   public final static int rootIOVersion = 1;

   /** Class name of referenced object */
   String getClassName();
}

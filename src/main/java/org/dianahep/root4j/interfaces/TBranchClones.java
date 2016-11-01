/*
 * Interface created by InterfaceBuilder. Do not modify.
 *
 * Created on Fri Jan 19 17:38:30 PST 2001
 */
package org.dianahep.root4j.interfaces;

// Note, the version generated from the StreamerInfo does not extend TBranch.
// This file has been modified by hand
public interface TBranchClones extends org.dianahep.root4j.RootObject, TNamed, TBranch
{
   TBranch getBranchCount();

   String getClassName();

   int getEntryOffset();

   Class getObjectClass();
}

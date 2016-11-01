/*
 * TTree.java
 *
 * Created on January 15, 2001, 6:58 PM
 */
package org.dianahep.root4j.reps;

import org.dianahep.root4j.core.AbstractRootObject;
import org.dianahep.root4j.interfaces.TBranch;


/**
 *
 * @author tonyj
 * @version $Id: TTreeRep.java 8584 2006-08-10 23:06:37Z duns $
 */
public abstract class TTreeRep extends AbstractRootObject implements org.dianahep.root4j.interfaces.TTree
{
   public TBranch getBranch(int index)
   {
      return (TBranch) getBranches().get(index);
   }

   public TBranch getBranch(String name)
   {
      org.dianahep.root4j.interfaces.TObjArray branches = getBranches();

      // TODO: Something more efficient
      for (int i = 0; i < branches.size(); i++)
      {
         TBranch branch = (TBranch) branches.get(i);
         if (branch.getName().equals(name))
            return branch;
      }
      throw new RuntimeException("Branch " + name + " not found in tree " + getName());
   }

   public int getNBranches()
   {
      return getBranches().size();
   }
}

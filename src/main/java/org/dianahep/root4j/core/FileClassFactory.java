package org.dianahep.root4j.core;

import org.dianahep.root4j.RootClassNotFound;
import org.dianahep.root4j.RootFileReader;
import org.dianahep.root4j.interfaces.TKey;
import org.dianahep.root4j.interfaces.TStreamerInfo;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A class factory used inside a RootFileReader.
 * It first attempts to resolve classes using the StreamerInfo from the file
 * itself, and if that fails delelgates to a default class factory.
 * 
 * - Upon initialization, retrieve the TList of TStreamerInfo objects
 * - Each TStreamerInfo object corresponds to 1 class and allows to describe the members
 *      of a ROOT object sitting in the ROOT FILE.
 * - Iterate thru the list and put the GenericRootClass object corresponding to each class
 *
 * @author tonyj
 * @version $Id: FileClassFactory.java 13849 2011-07-01 23:49:23Z tonyj $
 */
public class FileClassFactory implements RootClassFactory
{
   private Map classMap = new HashMap();
   private RootClassFactory defaultClassFactory;
   private RootFileReader rfr;

   public FileClassFactory(TKey streamerInfo, RootClassFactory defaultClassFactory, RootFileReader rfr) throws RootClassNotFound, IOException
   {
      this.defaultClassFactory = defaultClassFactory;
      this.rfr = rfr;

      // Loop over all the streamerInfo objects
      List tList = (List) streamerInfo.getObject();
      for (Iterator i = tList.iterator(); i.hasNext();)
      {
         Object element = i.next();
         if (element instanceof TStreamerInfo)
         {
            TStreamerInfo si = (TStreamerInfo) element;
            String key = si.getName();
            StreamerInfo info = new StreamerInfoNew(si);
            //  note, this does not find - it creates a GenericRootClass
            //  with the provides name=key and StreamerInfo=info
            classMap.put(key, DefaultClassFactory.findClass(key, info));
         }
      }

      // Now make sure we can resolve all the references
      Iterator i = classMap.values().iterator();
      while (i.hasNext())
      {
         GenericRootClass info = (GenericRootClass) i.next();
         try
         {
             // upon resolution - iterate thru the contents of the StreamerInfo
             // that corresponds to some class and collect all the members 
             // that are part of that class and all the superclasses
            info.resolve(this);
         }
         catch (RootClassNotFound x)
         {
            throw new RuntimeException("Could not resolve class "+x.getClassName()+" referenced from "+info.getClassName(),x);
         }
      }
   }

   public RootClassLoader getLoader()
   {
      return defaultClassFactory.getLoader();
   }

   public BasicRootClass create(String name) throws RootClassNotFound
   {
      BasicRootClass klass = (BasicRootClass) classMap.get(name);
      if (klass != null)
         return klass;
      return defaultClassFactory.create(name);
   }
}

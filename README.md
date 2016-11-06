# root4j - Pure Java [ROOT](https://root.cern.ch/) IO Library
*A fork of http://java.freehep.org/freehep-rootio/*

**Under rapid development :+1:**

## Description
root4j is a pure Java IO Library for [ROOT](https://root.cern.ch/) __with no binding__ to the original implementation of [ROOT](https://root.cern.ch/) in C++. Building on success of [ROOT](https://root.cern.ch/), we mimic the behavior of the classes from the original implementation. root4j utilizes the TStreamerInfo Record to infer the schema/description of Classes present and relies on JIT compilation of classes for objects requested to be read in. 

## Supported Features
- Large set of main ROOT Classes is available. 
  - TDirectory
  - TKey
  - TTree - TBranch/TBranchElement, etc.....
  - TStreamerInfo
  - ...
- Reading ROOT files.
- Using TStreamerInfo to infer the description of classes present in a ROOT file.
- JIT Compilation of classes for objects that are requested to be read in.
  - **There is an initial list of classes that do already have the streaming functionality(the rule for how to read the bytes and organize them into BasicTypes) implemented.**
  - TList is such an class - description.
- TTree support.
  - __Currently only old-style (thru leaves and baskets) exists for iterating thru the TTree entries.__

## RoadMap - TODO List
Our road map has 3 more or less generic topics to be addressed immediately: Core Functionality, Extra Functionality and Testing. __It is important to address that some features have description that references [spark-root](https://github.com/diana-hep/spark-root) as their full use will be demonstrated in there.__

### Core 
[] **Preparation for HDFS access** - Replace the use of `java.io.File` with a generic interface
[] **TRef Support for spark-root** - This applies to [spark-root](https://github.com/diana-hep/spark-root) for the most part. If something is to be added for the IO part, it has to come here.
[] **Demostrate the 3 levels TTree Hierarchy** - [ROOT](https://root.cern.ch/) treates Top branches of the tree differently from their subbranches - therefore this functionality has to be validated.
[] **Test [ROOT](https://root.cern.ch/) IO** - establish the testbed with various files coming from CMS, Atlas, etc... 
[] **Anything that points above do not cover**

[] **Tuning Phase**
[] **Deploy to the Central Maven Repository**

### Extra
- [] **Support for TByteBuffer** - Support for the memory management would allow us to be Garbage Collector independent, which in turn should give us a boost in performance!
- [] **Support for XRootD** - remote file streaming is used on a daily basis in any LHC Analysis.
- [] **Write Functionality** - provide support to write [ROOT](https://root.cern.ch/) files on disk.

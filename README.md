# root4j - Pure Java [ROOT](https://root.cern.ch/) IO Library
*A fork of http://java.freehep.org/freehep-rootio/*

**Under rapid development**

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
- TTree support - browse thru the Branches.
  - __Currently only old-style (thru leaves and baskets) exists for iterating thru the TTree entries.__

## RoadMap - TODO List

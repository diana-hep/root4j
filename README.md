# ROOT4J
*A fork of http://java.freehep.org/freehep-rootio/*

**No longer actively developed**

root4j is a pure Java IO Library for [ROOT](https://root.cern.ch/) __with no binding__ to the original implementation of [ROOT](https://root.cern.ch/) in C++. Building on success of [ROOT](https://root.cern.ch/), we mimic the behavior of the classes from the original implementation. root4j utilizes the TStreamerInfo Record to infer the schema/description of Classes present and relies on JIT compilation of classes for objects requested to be read in. 

The main use case of `root4j` is to pipe data in [ROOT](https://root.cern.ch/) TTrees into the Apache Spark Datasets with [spark-root](https://github.com/diana-hep/spark-root).

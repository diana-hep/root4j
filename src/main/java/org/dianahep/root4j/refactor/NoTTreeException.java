package org.dianahep.root4j.refactor;

public class NoTTreeException extends RuntimeException {
    public NoTTreeException(){
        super("No TTree Found");
    }
}

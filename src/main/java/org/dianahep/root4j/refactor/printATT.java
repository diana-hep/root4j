package org.dianahep.root4j.refactor;

public class printATT {
    static SRType att;

    public printATT(SRType att){
        this.att = att;
    }

    //Check how the objects are being compared - Currently comparing with static variables declared
    //Might have to change the attributes being compared to be calculated from buildATT.java

    public void toPrint(SRType att,int level,String sep){
        String toadd = "",toadd1 = "";
        for (int i=0;i<level;i++){
            toadd = toadd+sep;
        }
        for (int i=0;i<level+1;i++){
            toadd1 = toadd1 + sep;
        }
        SRNull srnull = new SRNull();
        if (att.equals(srnull)){
            System.out.println(toadd+"Null");
        }
        SRUnknown srunknown = new SRUnknown(SRType.name);
        if (att.equals(srunknown)){
            System.out.println(toadd + " " + SRType.name + "Unknown");
        }
        SRRoot srroot = new SRRoot(att.name,SRRoot.entries,SRRoot.types);
        if (att.equals(srroot)){
            System.out.println("Root : " + att.name + "with" + SRRoot.entries + "Entries");
            for (SRType t : SRRoot.types){
                toPrint(att,level+1,sep);
            }
        }
        SREmptyRoot sremptyroot = new SREmptyRoot(att.name,SREmptyRoot.entries);
        if (att.equals(sremptyroot)){
            System.out.println("Empty Root : " + att.name + " with " + SREmptyRoot.entries + " Entries ");
        }
        SRInt srint = new SRInt(att.name,SRInt.b,SRInt.l);
        if (att.equals(srint)){
            System.out.println(toadd + " " + att.name + " Integer");
        }
        SRString srstring = new SRString(att.name,SRString.b,SRString.l);
        if (att.equals(srstring)){
            System.out.println(toadd + " " + att.name + " String");
        }
        SRLong srlong = new SRLong(att.name,SRLong.b,SRLong.l);
        if (att.equals(srlong)){
            System.out.println(toadd + " " + att.name + " Long");
        }
        SRDouble srdouble = new SRDouble(att.name,SRDouble.b,SRDouble.l);
        if (att.equals(srdouble)){
            System.out.println(toadd + " " + att.name + " Double");
        }
        SRByte srbyte = new SRByte(att.name,SRByte.b,SRByte.l);
        if (att.equals(srbyte)){
            System.out.println(toadd + " " + att.name + ": Byte");
        }
        SRBoolean srboolean = new SRBoolean(att.name,SRBoolean.b,SRBoolean.l);
        if (att.equals(srboolean)){
            System.out.println(toadd + " " + att.name + ": Boolean");
        }
        SRFloat srfloat = new SRFloat(att.name,SRFloat.b,SRFloat.l);
        if (att.equals(srfloat)){
            System.out.println(toadd + " " + att.name + ": Float");
        }
        SRShort srshort = new SRShort(att.name,SRShort.b,SRShort.l);
        if (att.equals(srshort)){
            System.out.println(toadd + " " + att.name + ": Short");
        }
        SRArray srarray = new SRArray(att.name,SRArray.b,SRArray.l,SRArray.t,SRArray.n);
        if (att.equals(srarray)){
            System.out.println(toadd + " " + att.name + ": Array[" + SRArray.n + "]");
            toPrint(SRArray.t,level+1,sep);
        }
        SRVector srvector = new SRVector(att.name,SRVector.b,SRVector.t,SRVector.split,SRVector.isTop);
        if (att.equals(srvector)){
            System.out.println(toadd + " " + att.name + ": STL Vector. split=" + SRVector.split + " and isTop "+SRVector.isTop);
            toPrint(SRVector.t,level+1,sep);
        }
        SRMap srmap = new SRMap(att.name,SRMap.b,SRMap.keyType,SRMap.valueType,SRMap.split,SRMap.isTop);
        if (att.equals(srmap)){
            System.out.println(toadd + " " + att.name + ": Map " + SRMap.keyType.name+ " => " + SRMap.valueType.name + ". split=" + SRMap.split + " and is Top= "+SRMap.isTop);
            System.out.println(toadd1 + "KeyType =");
            toPrint(SRMap.keyType,level+2,sep);
            System.out.println(toadd1 + "ValueType =");
            toPrint(SRMap.valueType,level+2,sep);
        }
        SRSTLString srstlstring = new SRSTLString(att.name,SRSTLString.b,SRSTLString.isTop);
        if (att.equals(srstlstring)){
            System.out.println(toadd + " " + att.name + ": STL String isTop =" + SRSTLString.isTop);
        }
        SRComposite srcomposite = new SRComposite(att.name,SRComposite.b,SRComposite.members,SRComposite.split,SRComposite.isTop,SRComposite.isBase);
        if (att.equals(srcomposite)){
            System.out.println(toadd + " " + att.name + " Composite split = " + SRComposite.split + " isTop=" + SRComposite.isTop + "isBase = " + SRComposite.isBase);
            for (SRType t : SRComposite.members){
                toPrint(t,level+1,sep);
            }
        }
        System.out.println("Complete");
    }
}

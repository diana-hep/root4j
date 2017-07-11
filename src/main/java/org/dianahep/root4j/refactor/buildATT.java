package org.dianahep.root4j.refactor;

import org.dianahep.root4j.interfaces.*;
import java.util.*;
import java.io.*;
import org.dianahep.root4j.*;
import java.util.regex.*;

public class buildATT {
    TTree tree;
    String requiredColumns[];
    Map<String,TStreamerInfo> streamers;

    buildATT(TTree tree,String requiredColumns[],Map<String,TStreamerInfo> streamers){
        this.tree=tree;
        this.requiredColumns=requiredColumns;
        this.streamers=streamers;
    }

    SRType synthesizeLeafType(TBranch b,TLeaf leaf){
        String nameToUse;
        if (b.getLeaves().size()==1){
            nameToUse=b.getName();
        }
        else
        {
            nameToUse=leaf.getName();
        }
        switch (leaf.getRootClass().getClassName().charAt((leaf.getRootClass().getClassName()).length()-1)){
            case 'C' : SRString srstring = new SRString(nameToUse,b,leaf);
                        return srstring;
            case 'B' : SRByte srbyte1 = new SRByte(nameToUse,b,leaf);
                        return srbyte1;
            case 'b' : SRByte srbyte2 = new SRByte(nameToUse,b,leaf);
                        return srbyte2;
            case 'S' : SRShort srshort1 = new SRShort(nameToUse,b,leaf);
                        return srshort1;
            case 's' : SRShort srshort2 = new SRShort(nameToUse,b,leaf);
                        return srshort2;
            case 'I' : SRInt srint1 = new SRInt(nameToUse,b,leaf);
                        return srint1;
            case 'i' : SRInt srint2 = new SRInt(nameToUse,b,leaf);
                        return srint2;
            case 'F' : SRFloat srfloat = new SRFloat(nameToUse,b,leaf);
                        return srfloat;
            case 'D' : SRDouble srdouble = new SRDouble(nameToUse,b,leaf);
                        return srdouble;
            case 'L' : SRLong srlong1 = new SRLong(nameToUse,b,leaf);
                        return srlong1;
            case 'l' : SRLong srlong2 = new SRLong(nameToUse,b,leaf);
                        return srlong2;
            case 'O' : SRBoolean srboolean = new SRBoolean(nameToUse,b,leaf);
                        return srboolean;
            default : SRNull srnull = new SRNull();
                        return srnull;
        }
    }

    SRType synthesizeLeaf(TBranch b,TLeaf leaf){
        String nameToUse;
        if (b.getLeaves().size()==1){
            nameToUse=b.getName();
        }
        else{
            nameToUse=leaf.getName();
        }
        if (leaf instanceof TLeafElement){
            return synthesizeLeafElement(b,(TLeafElement)leaf);
        }
        else{
            if (leaf.getArrayDim()==0){
                return synthesizeLeafType(b,leaf);
            }
            else{
                return iterate(nameToUse,b,leaf,leaf.getArrayDim());
            }
        }
    }

    SRType iterate(String nameToUse,TBranch b,TLeaf leaf,int dimsToGo){
        if (dimsToGo==1){
            SRArray srarray = new SRArray(nameToUse,b,leaf,synthesizeLeafType(b,leaf),leaf.getMaxIndex()[leaf.getArrayDim()-1]);
            return srarray;
        }
        else
        {
            SRArray srarray = new SRArray(nameToUse,b,leaf,iterate(nameToUse,b,leaf,dimsToGo-1),leaf.getMaxIndex()[leaf.getArrayDim()-dimsToGo]);
            return srarray;
        }
    }

    SRType synthesizeLeafElement(TBranch b,TLeafElement leaf){
        SRNull srnull = new SRNull();
        return srnull;
    }

    SRType synthesizeTopBranch(TBranch b){
        SRRootType srroottype = new SRRootType();
        if (b instanceof TBranchElement){
            TBranchElement be = (TBranchElement)b;
            TStreamerInfo streamerInfo;
            try {
                streamerInfo = streamers.get(be.getClassName());
            }
            catch (NullPointerException e){
                streamerInfo = null;
            }
            if (streamerInfo==null){
                return synthesizeClassName(be.getClassName(),be,srroottype);
            }
            else {
                return synthesizeStreamerInfo(be,streamerInfo,null,srroottype);
            }
        }
        else {
            List<SRType> temp = new ArrayList();
            TObjArray leaves = b.getLeaves();
            if (leaves.size()>1){
                for (int i=0;i<leaves.size();i++){
                    temp.add(synthesizeLeaf(b,(TLeaf)leaves.get(i)));
                }
                SRComposite srcomposite = new SRComposite(b.getName(),b,temp,true,true);
                return srcomposite;
            }
            else {
                return synthesizeLeaf(b,(TLeaf)leaves.get(0));
            }
        }
    }

    SRType synthesizeBasicStreamerType(int typeCode){
        switch (typeCode){
            case 1 : SRByte srbyte1 = new SRByte("",null,null);
                        return srbyte1;
            case 2 : SRShort srshort1 = new SRShort("",null,null);
                        return srshort1;
            case 3 : SRInt srint1 = new SRInt("",null,null);
                        return srint1;
            case 4 : SRLong srlong1 = new SRLong("",null,null);
                        return srlong1;
            case 5 : SRFloat srfloat1 = new SRFloat("",null,null);
                        return srfloat1;
            case 6 : SRInt srint2 = new SRInt("",null,null);
                        return srint2;
            case 7 : SRString srstring = new SRString("",null,null);
                        return srstring;
            case 8 : SRDouble srdouble = new SRDouble("",null,null);
                        return srdouble;
            case 9 : SRFloat srfloat2 = new SRFloat("",null,null);
                        return srfloat2;
            case 10 : SRByte srbyte2 = new SRByte("",null,null);
                        return srbyte2;
            case 11 : SRByte srbyte3 = new SRByte("",null,null);
                        return srbyte3;
            case 12 : SRShort srshort2 = new SRShort("",null,null);
                        return srshort2;
            case 13 : SRInt srint3 = new SRInt("",null,null);
                        return srint3;
            case 14 : SRLong srlong2 = new SRLong("",null,null);
                        return srlong2;
            case 15 : SRInt srint4 = new SRInt("",null,null);
                        return srint4;
            case 16 : SRLong srlong3 = new SRLong("",null,null);
                        return srlong3;
            case 17 : SRLong srlong4 = new SRLong("",null,null);
                        return srlong4;
            case 18 : SRBoolean srboolean = new SRBoolean("",null,null);
                        return srboolean;
            case 19 : SRShort srshort3 = new SRShort("",null,null);
                        return srshort3;
            default : SRNull srnull = new SRNull();
                        return srnull;
        }
    }

    String formatNameForPointer(String className){
        if (className.charAt(className.length()-1)=='*'){
            String formattedClassName="";
            for (int i=0;i<className.length()-2;i++){
                formattedClassName = formattedClassName+className.charAt(i);
            }
            return formattedClassName;
        }
        else{
            return className;
        }
    }

    SRType synthesizeStreamerElement(TBranchElement b,TStreamerElement streamerElement,SRTypeTag parentType){
        Map<String,SRType> customStreamers = new HashMap();
        SRInt srintm = new SRInt("",null,null);
        customStreamers.put("trigger::TriggerObjectType",srintm);
        customStreamers.put("reco::Muon::MuonTrackType",srintm);
        customStreamers.put("pat::IsolationKeys",srintm);
        customStreamers.put("reco::IsoDeposit",srintm);
        SRShort srshortm1 = new SRShort("f1",null,null);
        SRShort srshortm2 = new SRShort("f2",null,null);
        SRInt srintm3 = new SRInt("f3",null,null);
        SRShort srshortm4 = new SRShort("f4",null,null);
        List<SRType> tempparameter = new ArrayList();
        tempparameter.add(srshortm1);
        tempparameter.add(srshortm2);
        tempparameter.add(srintm3);
        tempparameter.add(srshortm4);
        SRComposite srcompositem=new SRComposite("product_",null,tempparameter,false,false,false);
        customStreamers.put("edm::RefCoreWithIndex",srcompositem);
        TLeafElement temp;
        if (b==null){
            temp=null;
        }
        else {
            temp=(TLeafElement)b.getLeaves().get(0);
        }
        switch (streamerElement.getType()){
            case 0 :
                TStreamerInfo streamerInfo;
                try {
                    streamerInfo = streamers.get(streamerElement.getName());
                }
                catch (NullPointerException e){
                    streamerInfo = null;
                }
                if (streamerInfo == null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else {
                    if (streamerInfo.getElements().size()==0){
                        SRComposite srcomposite = new SRComposite(streamerElement.getName(),b,null,false,false,true);
                        return srcomposite;
                    }
                    else {
                        return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType);
                    }
                }
                break;
            case 1 :
                SRByte srbyte = new SRByte(streamerElement.getName(),b,temp);
                return srbyte;
            case 2 :
                SRShort srshort = new SRShort(streamerElement.getName(),b,temp);
                return srshort;
            case 3 :
                SRInt srint = new SRInt(streamerElement.getName(),b,temp);
                return srint;
            case 4 :
                SRLong srlong = new SRLong(streamerElement.getName(),b,temp);
                return srlong;
            case 5 :
                SRFloat srfloat = new SRFloat(streamerElement.getName(),b,temp);
                return srfloat;
            case 6 :
                SRInt srint2 = new SRInt(streamerElement.getName(),b,temp);
                return srint2;
            case 7 :
                SRString srstring = new SRString(streamerElement.getName(),b,temp);
                return srstring;
            case 8 :
                SRDouble srdouble = new SRDouble(streamerElement.getName(),b,temp);
                return srdouble;
            case 9 :
                SRFloat srfloat2 = new SRFloat(streamerElement.getName(),b,temp);
                return srfloat2;
            case 10 :
                SRByte srbyte2 = new SRByte(streamerElement.getName(),b,temp);
                return srbyte2;
            case 11 :
                SRByte srbyte3 = new SRByte(streamerElement.getName(),b,temp);
                return srbyte3;
            case 12 :
                SRShort srshort2  = new SRShort(streamerElement.getName(),b,temp);
                return srshort2;
            case 13 :
                SRInt srint3 = new SRInt(streamerElement.getName(),b,temp);
                return srint3;
            case 14 :
                SRLong srlong2 = new SRLong(streamerElement.getName(),b,temp);
                return srlong2;
            case 15 :
                SRInt srint4 = new SRInt(streamerElement.getName(),b,temp);
                return srint4;
            case 16 :
                SRLong srlong3 = new SRLong(streamerElement.getName(),b,temp);
                return srlong3;
            case 17 :
                SRLong srlong4 = new SRLong(streamerElement.getName(),b,temp);
                return srlong4;
            case 18 :
                SRBoolean srboolean = new SRBoolean(streamerElement.getName(),b,temp);
                return srboolean;
            case 19 :
                SRShort srshort3 = new SRShort(streamerElement.getName(),b,temp);
                return srshort3;
            case 21 :
            case 22 :
            case 23 :
            case 24 :
            case 25 :
            case 26 :
            case 27 :
            case 28 :
            case 29 :
            case 30 :
            case 31 :
            case 32 :
            case 33 :
            case 34 :
            case 35 :
            case 36 :
            case 37 :
            case 38 :
            case 39 :
            case 40 :
                    return iterateArray(b,streamerElement,parentType,streamerElement.getArrayDim());tr
            case 61 :
                try {
                    streamerInfo = streamers.get(formatNameForPointer(streamerElement.getTypeName()));
                }
                catch (NullPointerException e){
                    streamerInfo=null;
                }
                if (streamerInfo==null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else {
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType);
                }
            case 62 :
                try {
                    streamerInfo = streamers.get(formatNameForPointer(streamerElement.getTypeName()));
                }
                catch (NullPointerException e){
                    streamerInfo = null;
                }
                if (streamerInfo==null) {
                    SRType isCustom;
                    try {
                        isCustom = customStreamers.get(formatNameForPointer(streamerElement.getTypeName()));
                        return isCustom;
                    } catch (NullPointerException e){
                        SRNull srnull = new SRNull();
                        isCustom = srnull;
                        SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                        return srunknown;
                    }
                }
                else {
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType);
                }
            case 63 :
                try{
                    streamerInfo = streamers.get(formatNameForPointer(streamerElement.getTypeName()));
                }
                catch (NullPointerException e){
                    streamerInfo = null;
                }
                if (streamerInfo == null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else {
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType);
                }
            case 65 :
                SRString srstring2 = new SRString(streamerElement.getName(),b,temp);
                return srstring2;
            case 66 :
                try {
                    streamerInfo = streamers.get(streamerElement.getName());
                }
                catch (NullPointerException e){
                    streamerInfo = null;
                }
                if (streamerInfo == null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else {
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType);
                }
            case 67 :
                try {
                    streamerInfo = streamers.get(streamerElement.getName());
                }
                catch (NullPointerException e){
                    streamerInfo = null;
                }
                if (streamerInfo == null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else{
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType);
                }
            case 69 :
                try {
                    streamerInfo = streamers.get(formatNameForPointer(streamerElement.getTypeName()));
                }
                catch (NullPointerException e){
                    streamerInfo = null;
                }
                if (streamerInfo == null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else {
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType);
                }
            case 500 :
                return synthesizeStreamerSTL(b,(TStreamerSTL)streamerElement,parentType);
            default :
                SRUnknown srunknown = new SRUnknown("Unidentified StreamerElement type");
                return srunknown;
        }
    }


    SRType iterateArray(TBranchElement b,TStreamerElement streamerElement,SRTypeTag parentType,int dimsToGo){
        TLeaf temp;
        if (dimsToGo == 1){
            if (b==null){
                temp=null;
            }
            else{
                temp =(TLeafElement) b.getLeaves().get(0);
            }
            SRArray srarray = new SRArray(streamerElement.getName(),b,temp,synthesizeBasicStreamerType(streamerElement.getType()-20),streamerElement.getMaxIndex()[streamerElement.getArrayDim()-1]);
            return srarray;
        }
        else{
            if (b==null){
                temp=null;
            }
            else{
                temp = (TLeafElement)b.getLeaves().get(0);
            }
            SRArray srarray = new SRArray(streamerElement.getName(),b,temp,iterateArray(b,streamerElement,parentType,dimsToGo-1),streamerElement.getMaxIndex()[streamerElement.getArrayDim()-dimsToGo]);
            return srarray;
        }
    }

    SRType synthesizeBasicTypeName(String typeName){
        if (typeName.equals("int") || typeName.equals("unsigned int")){
            SRInt srint = new SRInt("",null,null);
            return srint;
        }
        else if (typeName.equals("float") || typeName.equals("Double32_t")){
            SRFloat srfloat = new SRFloat("",null,null);
            return srfloat;
        }
        else if (typeName.equals("double")){
            SRDouble srdouble = new SRDouble("",null,null);
            return srdouble;
        }
        else if (typeName.equals("char") || typeName.equals("unsigned char")){
            SRByte srbyte = new SRByte("",null,null);
            return srbyte;
        }
        else if (typeName.equals("long") || typeName.equals("unsigned long")){
            SRLong srlong = new SRLong("",null,null);
            return srlong;
        }
        else if (typeName.equals("short") || typeName.equals("unsigned short")){
            SRShort srshort = new SRShort("",null,null);
            return srshort;
        }
        else if (typeName.equals("bool")){
            SRBoolean srbool = new SRBoolean("",null,null);
            return srbool;
        }
        else{
            SRNull srnull = new SRNull();
            return srnull;
        }
    }

    List<String> iterate(String fullTemplateString, int n,Integer from,Integer currentPos,List<String> acc){
        if (currentPos==fullTemplateString.length()){
            acc.add(fullTemplateString.substring(from));
            return acc;
        }
        else if (fullTemplateString.charAt(currentPos)==','){
            if (n==0){
                acc.add(fullTemplateString.substring(from,currentPos));
                return iterate(fullTemplateString,0,currentPos+1,currentPos+1,acc);
            }
            else{
               return iterate(fullTemplateString,n,from,currentPos,acc);
            }
        }
        else if (fullTemplateString.charAt(currentPos)=='<' || fullTemplateString.charAt(currentPos)=='>'){
            return iterate(fullTemplateString,n-1,from,currentPos+1,acc);
        }
        else {
            return iterate(fullTemplateString,n,from,currentPos+1,acc);
        }
    }

    List<String> extractTemplateArguements(String fullTemplateString){
        List<String> arrreturn=new ArrayList();
        return iterate(fullTemplateString,0,0,0,arrreturn);
    }

    SRType synthesizeClassName(String className,TBranchElement b,SRTypeTag parentType){
        List<String> stlLinear= Arrays.asList("vector","list","deque","set","multiset","forward_list","unordered_set","unordered_multiset");
        List<String> stlAssociative = Arrays.asList("map","unordered map","multimap","unordered_multimap");
        String stlPair = "pair";
        String stlBitset = "bitset";
        List<String> stlStrings = Arrays.asList("string","_basic_string_common<true>");
        String classTypeRE = Pattern.quote("(.*?)<(.*?)>");
        String classTypeString,arguementsTypeString;

    }

    SRType synthesizeBranchElement(TBranchElement b,TStreamerElement streamerElement,SRTypeTag parentType){
        TObjArray subs=b.getBranches();
        if (streamerElement == null){
            SRNull srnull = new SRNull();
            return srnull;
        }
        return synthesizeStreamerElement(b,streamerElement,parentType);
    }

    TTree findTree(TDirectory dir)throws RootClassNotFound,IOException{
        for (int i=0;i<dir.nKeys();i++){
            TKey key = dir.getKey(i);
            if (key.getObjectClass().getClassName().equals("TDirectory")){
                return findTree((TDirectory)key.getObject());
            }
            else if (key.getObjectClass().getClassName().equals("TTree")){
                return (TTree)key.getObject();
            }
        }
        return null;
    }

    void columns(){
        if (requiredColumns==null){

        }
    }


}

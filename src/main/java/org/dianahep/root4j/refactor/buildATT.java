package org.dianahep.root4j.refactor;

import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
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
            catch (ArrayIndexOutOfBoundsException e){
                streamerInfo = null;
            }
            if (streamerInfo==null){
                return synthesizeClassName(be.getClassName(),be,srroottype);
            }
            else {
                return synthesizeStreamerInfo(be,streamerInfo,null,srroottype,false);
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
                catch (ArrayIndexOutOfBoundsException e){
                    streamerInfo = null;
                }
                if (streamerInfo == null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else {
                    if (streamerInfo.getElements().size()==0){
                        List<SRType> sub = new ArrayList();
                        SRComposite srcomposite = new SRComposite(streamerElement.getName(),b,sub,false,false,true);
                        return srcomposite;
                    }
                    else {
                        return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType,false);
                    }
                }
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
                    return iterateArray(b,streamerElement,parentType,streamerElement.getArrayDim());
            case 61 :
                try {
                    streamerInfo = streamers.get(formatNameForPointer(streamerElement.getTypeName()));
                }
                catch (ArrayIndexOutOfBoundsException e){
                    streamerInfo=null;
                }
                if (streamerInfo==null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else {
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType,false);
                }
            case 62 :
                try {
                    streamerInfo = streamers.get(formatNameForPointer(streamerElement.getTypeName()));
                }
                catch (ArrayIndexOutOfBoundsException e){
                    streamerInfo = null;
                }
                if (streamerInfo==null) {
                    SRType isCustom;
                    try {
                        isCustom = customStreamers.get(formatNameForPointer(streamerElement.getTypeName()));
                        return isCustom;
                    } catch (ArrayIndexOutOfBoundsException e){
                        SRNull srnull = new SRNull();
                        isCustom = srnull;
                        SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                        return srunknown;
                    }
                }
                else {
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType,false);
                }
            case 63 :
                try{
                    streamerInfo = streamers.get(formatNameForPointer(streamerElement.getTypeName()));
                }
                catch (ArrayIndexOutOfBoundsException e){
                    streamerInfo = null;
                }
                if (streamerInfo == null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else {
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType,false);
                }
            case 65 :
                SRString srstring2 = new SRString(streamerElement.getName(),b,temp);
                return srstring2;
            case 66 :
                try {
                    streamerInfo = streamers.get(streamerElement.getName());
                }
                catch (ArrayIndexOutOfBoundsException e){
                    streamerInfo = null;
                }
                if (streamerInfo == null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else {
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType,false);
                }
            case 67 :
                try {
                    streamerInfo = streamers.get(streamerElement.getName());
                }
                catch (ArrayIndexOutOfBoundsException e){
                    streamerInfo = null;
                }
                if (streamerInfo == null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else{
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType,false);
                }
            case 69 :
                try {
                    streamerInfo = streamers.get(formatNameForPointer(streamerElement.getTypeName()));
                }
                catch (ArrayIndexOutOfBoundsException e){
                    streamerInfo = null;
                }
                if (streamerInfo == null){
                    SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                    return srunknown;
                }
                else {
                    return synthesizeStreamerInfo(b,streamerInfo,streamerElement,parentType,false);
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
        String classTypeString,arguementsTypeString;

        String classTypeRE = Pattern.quote("(.*?)<(.*?)>");
        boolean t1 = Pattern.matches(classTypeRE,"aaa");
        boolean t2 = Pattern.matches(classTypeRE,"bbb");

        if (t1 && t2){
            classTypeString = "aaa";
            arguementsTypeString = "bbb".trim();
        }
        else {
            classTypeString = null;
            arguementsTypeString = null;
        }

        SRCollectionType srcollectiontype = new SRCollectionType();
        if (stlStrings.contains(className)){
            if (b==null){
                if (parentType.equals(srcollectiontype)){
                    SRSTLString srstlstring = new SRSTLString("",null,false);
                    return srstlstring;
                }
                else {
                    SRSTLString srstlstring = new SRSTLString("",null,true);
                    return srstlstring;
                }
            }
            else {
                if (parentType.equals(srcollectiontype)){
                    SRSTLString srstlstring = new SRSTLString(b.getName(),b,false);
                    return srstlstring;
                }
                else {
                    SRSTLString srstlstring = new SRSTLString(b.getName(),b,true);
                    return srstlstring;
                }
            }
        }
        SRType isCustom;
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
        SRNull srnull = new SRNull();
        try {
            isCustom = customStreamers.get(className);
        }
        catch (ArrayIndexOutOfBoundsException e){
            isCustom = srnull;
        }
        if (isCustom != srnull){
            return isCustom;
        }
        if (classTypeString == null || arguementsTypeString == null){
            SRUnknown srunknown = new SRUnknown(className);
            return srunknown;
        }
        if (stlBitset.equals(classTypeString)){
            return synthesizeClassName("vector<bool>", b, parentType);
        }
        else if (stlLinear.contains(classTypeString)){
            List<String> templateArguements = extractTemplateArguements(arguementsTypeString);
            String valueTypeName;
            if (templateArguements.size()>0) {
                valueTypeName = templateArguements.get(0);
            }
            else {
                valueTypeName = arguementsTypeString;
            }
            TStreamerInfo streamerInfo;
            try {
                streamerInfo = streamers.get(formatNameForPointer(valueTypeName));
            }
            catch (ArrayIndexOutOfBoundsException e) {
                streamerInfo = null;
            }
            SRType valueType ;
            if (streamerInfo == null) {
                SRType basicType = synthesizeBasicTypeName(valueTypeName);
                if (basicType.equals(srnull)) {
                    valueType = synthesizeClassName(valueTypeName, null, srcollectiontype);
                }
                else {
                    valueType = basicType;
                }
            }
            else {
                valueType = synthesizeStreamerInfo(b, streamerInfo, null, srcollectiontype, false);
            }
            if (b==null){
                if (parentType.equals(srcollectiontype)) {
                    SRVector srvector = new SRVector("", b, valueType, false, false);
                    return srvector;
                }
                else {
                    SRVector srvector = new SRVector("", b, valueType, false, true);
                    return srvector;
                }
            }
            else {
                if (parentType.equals(srcollectiontype)) {
                    SRVector srvector = new SRVector(b.getName(), b, valueType, false, false);
                    return srvector;
                }
                else{
                    boolean temp;
                    if (b.getBranches().size()==0) {
                        temp = false;
                    }
                    else {
                        temp = true;
                    }
                    SRVector srvector = new SRVector(b.getName(),b,valueType,temp,true);
                    return srvector;
                }
             }
        }
        else if (stlAssociative.contains(classTypeString)){
            boolean isMulti = classTypeString.contains("multi");
            List<String> templateArguements = extractTemplateArguements(arguementsTypeString);
            String keyTypeString;
            if (templateArguements.size()>2){
                keyTypeString = templateArguements.get(0);
            }
            else {
                keyTypeString = null;
            }
            String valueTypeString;
            if (templateArguements.size()>2){
                valueTypeString = templateArguements.get(1);
            }
            else {
                valueTypeString = null;
            }
            if (keyTypeString == null || valueTypeString == null){
                return srnull;
            }
            TStreamerInfo keyStreamerInfo;
            try {
                keyStreamerInfo = streamers.get(formatNameForPointer(keyTypeString));
            }
            catch (NullPointerException e){
                keyStreamerInfo = null;
            }
            SRType keyType;
            if (keyStreamerInfo == null){
                if (synthesizeBasicTypeName(keyTypeString).equals(srnull)){
                    keyType = synthesizeClassName(keyTypeString,null,srcollectiontype);
                }
                else {
                    keyType = synthesizeBasicTypeName(keyTypeString);
                }
            }
            else {
                keyType = synthesizeStreamerInfo(null,keyStreamerInfo,null,srcollectiontype,false);
            }
            TStreamerInfo streamerInfo;
            try {
                streamerInfo = streamers.get(formatNameForPointer(valueTypeString));
            }
            catch (ArrayIndexOutOfBoundsException e){
                streamerInfo = null;
            }
            SRType valueType;
            if (streamerInfo == null){
                SRType basicType = synthesizeBasicTypeName(valueTypeString);
                if (basicType.equals(srnull)){
                    valueType = synthesizeClassName(valueTypeString,null,srcollectiontype);
                }
                else {
                    valueType = basicType;
                }
            }
            else {
                valueType = synthesizeStreamerInfo(b,streamerInfo,null,srcollectiontype,false);
            }
            if (b==null){
                if (parentType.equals(srcollectiontype)){
                    if (isMulti){
                        SRMultiMap srmultimap = new SRMultiMap("",b,keyType,valueType,false,true);
                        return srmultimap;
                    }
                    else {
                        SRMap srmap = new SRMap("",b,keyType,valueType,false,false);
                        return srmap;
                    }
                }
                else {
                    if (isMulti){
                        SRMultiMap srmultimap = new SRMultiMap("",b,keyType,valueType,false,true);
                        return srmultimap;
                    }
                    else {
                        SRMap srmap = new SRMap("",b,keyType,valueType,false,true);
                        return srmap;
                    }
                }
            }
            else {
                if (parentType.equals(srcollectiontype)){
                    if (isMulti){
                        SRMultiMap srmultimap = new SRMultiMap(b.getName(),b,keyType,valueType,false,true);
                        return srmultimap;
                    }
                    else {
                        SRMap srmap = new SRMap(b.getName(),b,keyType,valueType,false,false);
                        return srmap;
                    }
                }
                else {
                    if (isMulti){
                        boolean temp;
                        if (b.getBranches().size()==0){
                            temp=false;
                        }
                        else {
                            temp = true;
                        }
                        SRMultiMap srmultimap = new SRMultiMap(b.getName(),b,keyType,valueType,temp,true);
                        return srmultimap;
                    }
                    else {
                        boolean temp;
                        if (b.getBranches().size()==0){
                            temp=false;
                        }
                        else {
                            temp = true;
                        }
                        SRMap srmap = new SRMap(b.getName(),b,keyType,valueType,temp,true);
                        return srmap;
                    }
                }
            }
        }
        else if (classTypeString.equals(stlPair)){
            List<String> templateArguements = extractTemplateArguements(arguementsTypeString);
            String firstTypeString;
            if (templateArguements.size()==2){
                firstTypeString = templateArguements.get(0);
            }
            else {
                firstTypeString = null;
            }
            String secondTypeString;
            if (templateArguements.size()==2){
                secondTypeString = templateArguements.get(1);
            }
            else {
                secondTypeString = null;
            }
            if (firstTypeString == null || secondTypeString == null){
                return srnull;
            }
            TStreamerInfo streamerInfoFirst;
            try {
                streamerInfoFirst = streamers.get(formatNameForPointer(firstTypeString));
            }
            catch (ArrayIndexOutOfBoundsException e){
                streamerInfoFirst = null;
            }
            TStreamerInfo streamerInfoSecond;
            try {
                streamerInfoSecond = streamers.get(formatNameForPointer(secondTypeString));
            }
            catch (ArrayIndexOutOfBoundsException e){
                streamerInfoSecond = null;
            }
            SRType firstType;
            TBranchElement temp;
            SRCompositeType srcompositetype = new SRCompositeType();
            if (streamerInfoFirst == null){
                SRType basicType = synthesizeBasicTypeName(firstTypeString);
                if (basicType.equals(srnull)){
                    if (b==null || b.getBranches().size()==0){
                        temp = null;
                    }
                    else {
                        temp = (TBranchElement)b.getBranches().get(0);
                    }
                    firstType = synthesizeClassName(firstTypeString,temp,srcompositetype);
                }
                else {
                    firstType = basicType;
                }
            }
            else {
                firstType = synthesizeStreamerInfo(null,streamerInfoFirst,null,srcollectiontype,false);
            }
            SRType secondType;
            if (streamerInfoSecond == null){
                SRType basicType = synthesizeBasicTypeName(secondTypeString);
                if (basicType.equals(srnull)){
                        if (b==null || b.getBranches().size()==0){
                            temp = null;
                        }
                        else {
                            temp = (TBranchElement)b.getBranches().get(1);
                        }
                        secondType = synthesizeClassName(secondTypeString,temp,srcompositetype);
                }
                else {
                        secondType = basicType;
                }
            }
            else{
                secondType = synthesizeStreamerInfo(null,streamerInfoSecond,null,srcompositetype,false);
            }

            Pair<SRType,SRType> pair = new Pair(firstType,secondType);
            pair.setL(firstType);
            pair.setR(secondType);
            List<Pair<SRType,SRType>> p = new ArrayList();

            SRRootType srroottype = new SRRootType();
            if (b==null){
                if (parentType.equals(srcollectiontype)){
                    SRComposite srcomposite = new SRComposite("",b,p,false,false);
                    return srcomposite;
                }
                else {
                    SRComposite srcomposite = new SRComposite("",b,p,false,false);
                    return srcomposite;
                }
            }
            else {
                if (parentType.equals(srcollectiontype)){
                    boolean tempbool;
                    if (b.getBranches().size()==0){
                        tempbool = false;
                    }
                    else {
                        tempbool = true;
                    }
                    SRComposite srcomposite = new SRComposite(b.getName(),b,p,tempbool,false);
                    return srcomposite;
                }
                else if (parentType.equals(srcollectiontype)){
                    boolean tempbool;
                    if (b.getBranches().size()==0){
                        tempbool = false;
                    }
                    else {
                        tempbool = true;
                    }
                    SRComposite srcomposite = new SRComposite(b.getName(),b,p,tempbool,true);
                    return srcomposite;
                }
                else {
                    boolean tempbool;
                    if (b.getBranches().size()==0){
                        tempbool = false;
                    }
                    else {
                        tempbool = true;
                    }
                    SRComposite srcomposite = new SRComposite(b.getName(),b,p,tempbool,false);
                    return srcomposite;
                }
            }
        }
        return srnull;
    }

    SRType synthesizeStreamerInfo(TBranchElement b,TStreamerInfo streamerInfo,TStreamerElement streamerElement,SRTypeTag parentType,boolean flattenable){
        TObjArray elements = streamerInfo.getElements();
        for (int i=0;i<elements.size();i++){
            TStreamerElement x = (TStreamerElement)elements.get(i);
            String typeName = x.getTypeName();
            String s="vector";
            String temp="<$";
            temp=temp+streamerInfo.getName();
            temp=temp+">";
            String t = s+temp;
            if (typeName.equals(t) || typeName.equals(temp)){
                SRNull srnull = new SRNull();
                return srnull;
            }
        }
        if (elements.size()==0){
            String temp;
            boolean t;
            if (streamerElement!=null){
                temp=streamerElement.getName();
            }
            else {
                temp="<$";
                temp=temp+streamerInfo.getName();
                temp=temp+">";
            }
            if (streamerElement!=null){
                if (streamerElement.getType()==0){
                    t=true;
                }
                else {
                    t=false;
                }
            }
            else {
                t=false;
            }
            List<SRType> sub = new ArrayList();
            SRComposite srcomposite = new SRComposite(temp,b,sub,false,false,t);
            return srcomposite;
        }
        else if (((TStreamerElement)elements.get(0)).getName().equals("This")){
            return synthesizeStreamerElement(b,(TStreamerElement)elements.get(0),parentType);
        }
        else if (streamerInfo.getName().equals("TClonesArray")){
            if (b==null){
                SRNull srnull = new SRNull();
                return srnull;
            }
            else {
                String typeName = b.getClonesName();
                String nameToSynthesize = "vector<$";
                nameToSynthesize = nameToSynthesize+typeName;
                nameToSynthesize = nameToSynthesize+">";
                return synthesizeClassName(nameToSynthesize,b,parentType);
            }
        }
        else {
            if (b==null){
                String temp1;
                if (streamerElement==null){
                    temp1="";
                }
                else {
                    temp1 = streamerElement.getName();
                }
                List<SRType> temp = new ArrayList();
                SRCompositeType srcompositetype = new SRCompositeType();
                for (int i=0;i<elements.size();i++){
                    temp.add(synthesizeStreamerElement(null,(TStreamerElement)elements.get(i),srcompositetype));
                }
                boolean temp2;
                if (streamerElement==null){
                    temp2=false;
                }
                else if (streamerElement.getType()==0){
                    temp2=true;
                }
                else{
                    temp2=false;
                }
                SRComposite srcomposite = new SRComposite(temp1,null,temp,false,false,temp2);
                return srcomposite;
            }
            else if (b.getBranches().size()==0){
                List<SRType> temp = new ArrayList();
                SRCompositeType srcompositetype = new SRCompositeType();
                for (int i=0;i<elements.size();i++){
                    temp.add(synthesizeStreamerElement(null,(TStreamerElement)elements.get(i),srcompositetype));
                }
                SRRootType srroottype = new SRRootType();
                boolean t;
                if (parentType.equals(srroottype)){
                    t=true;
                }
                else {
                    t=false;
                }
                SRComposite srcomposite = new SRComposite(b.getName(),b,temp,false,t);
                return srcomposite;
            }
            else {
                if (b.getType()==1 || b.getType()==2 || b.getType()==3 || b.getType()==4){
                    return synthesizeFlattenable(b,streamerInfo);
                }
                else {
                    List<SRType> temp = new ArrayList();
                    SRCompositeType srcompositetype = new SRCompositeType();
                    for (int i=0;i<b.getBranches().getSize();i++){
                        TBranchElement sub = (TBranchElement)b.getBranches().get(i);
                        temp.add(synthesizeStreamerElement(sub,(TStreamerElement)elements.get(sub.getID()),srcompositetype));
                    }
                    boolean t;
                    SRRootType srroottype = new SRRootType();
                    if (parentType.equals(srroottype)){
                        t=true;
                    }
                    else {
                        t=false;
                    }
                    SRComposite srcomposite = new SRComposite(b.getName(),b,temp,true,t);
                    return srcomposite;
                }
            }
        }
    }

    SRType synthesizeStreamerSTL(TBranchElement b,TStreamerSTL streamerSTL,SRTypeTag parentType){
        switch (streamerSTL.getSTLtype()){
            case 1 :
                int ctype = streamerSTL.getCtype();
                SRType t;
                if (ctype<61){
                    if (streamerSTL.getTypeName().contains("bool") && ctype==21){
                        t=synthesizeBasicStreamerType(18);
                    }
                    else {
                        t=synthesizeBasicStreamerType(ctype);
                    }
                }
                else {
                    String memberClassName="";
                    for (int i=streamerSTL.getTypeName().indexOf('<')+1;i<streamerSTL.getTypeName().length()-1;i++){
                        memberClassName=memberClassName+streamerSTL.getTypeName().charAt(i);
                    }
                    memberClassName = memberClassName.trim();
                    TStreamerInfo streamerInfo;
                    try {
                        streamerInfo = streamers.get(formatNameForPointer(memberClassName));
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        streamerInfo = null;
                    }
                    if (streamerInfo == null){
                        SRCollectionType srcollectiontype = new SRCollectionType();
                        t=synthesizeClassName(memberClassName,null,srcollectiontype);
                    }
                    else {
                        SRCollectionType srcollectiontype = new SRCollectionType();
                        TBranchElement temp;
                        if (b==null || b.getBranches().size()==0){
                            temp = null;
                        }
                        else {
                            temp = b;
                        }
                        t=synthesizeStreamerInfo(temp,streamerInfo,null,srcollectiontype,false);
                    }
                }
                if (b==null){
                        SRCollectionType srcollectiontype = new SRCollectionType();
                        if (parentType.equals(srcollectiontype)){
                            SRVector srvector = new SRVector("",b,t,false,false);
                            return srvector;
                    }
                    else {
                            SRVector srvector = new SRVector(streamerSTL.getName(),b,t,false,true);
                            return srvector;
                        }
                }
                else {
                    SRCollectionType srcollectiontype = new SRCollectionType();
                    if (parentType.equals(srcollectiontype)){
                        SRVector srvector = new SRVector(b.getName(),b,t,false,false);
                        return srvector;
                    }
                    else {
                        boolean temp;
                        if (b.getBranches().size()==0){
                            temp = false;
                        }
                        else {
                            temp = true;
                        }
                        SRVector srvector = new SRVector(b.getName(),b,t,temp,true);
                        return srvector;
                    }
                }
            case 4 :
                return synthesizeClassName(streamerSTL.getTypeName(),b,parentType);
            case 5 :
                return synthesizeClassName(streamerSTL.getTypeName(),b,parentType);
            case 6 :
                return synthesizeClassName(streamerSTL.getTypeName(),b,parentType);
            case 8 :
                return synthesizeClassName("vector<bool>",b,parentType);
            case 365 :
                if (b==null){
                    SRCollectionType srcollectiontype = new SRCollectionType();
                    if (parentType.equals(srcollectiontype)){
                        SRSTLString srstlstring = new SRSTLString("",null,false);
                        return srstlstring;
                    }
                    else {
                        SRSTLString srstlstring = new SRSTLString(streamerSTL.getName(),null,true);
                        return srstlstring;
                    }
                }
                else {
                    SRCollectionType srcollectiontype = new SRCollectionType();
                    if (parentType.equals(srcollectiontype)){
                        SRSTLString srstlstring = new SRSTLString(b.getName(),b,false);
                        return srstlstring;
                    }
                    else {
                        SRSTLString srstlstring = new SRSTLString(streamerSTL.getName(),b,true);
                        return srstlstring;
                    }
                }
            default :
                SRNull srnull = new SRNull();
                return srnull;
        }
    }

    SRType synthesizeFlattenable(TBranchElement b,TStreamerInfo streamerInfo){
        List<String> sub = new ArrayList();
        SRComposite srcomposite = new SRComposite(b.getName(),b,iterate(streamerInfo,sub,b),true,false);
        return srcomposite;
    }

    TBranchElement findBranch(String objectName,List<String> history,TBranchElement b){
        String fullName="";
        String temp="";
        List<String> t1 = new ArrayList();
        if (b.getType()==1){
            int t=0;
            int stub=0;
            for (int i=0;i<b.getName().length();i++){
                if (b.getName().charAt(i)=='.'){
                    t++;
                }
            }
            if (t==0){
                history.add(objectName);
                t1.addAll(history);
            }
            else {
                for (int i=0;i<b.getName().length();i++){
                    if (b.getName().charAt(i)=='.'){
                        stub=i;
                        break;
                    }
                }
                for (int i=0;i<stub;i++){
                    temp=temp+b.getName().charAt(i);
                }
                t1.add(temp);
                temp="";
                for (int i=stub+1;i<b.getName().length();i++){
                    temp = temp+b.getName().charAt(i);
                }
                t1.add(temp);
                t1.addAll(history);
                t1.add(objectName);
            }
        }
        else {
            int t=0;
            int stub=0;
            for (int i=0;i<b.getName().length();i++){
                if (b.getName().charAt(i)=='.'){
                    t++;
                }
            }
            if (t==0){
                t1.add(b.getName());
                t1.addAll(history);
                t1.add(objectName);
            }
            else {
                for (int i=0;i<b.getName().length();i++){
                    if (b.getName().charAt(i)=='.'){
                        stub=i;
                        break;
                    }
                }
                for (int i=0;i<stub;i++){
                    temp=temp+b.getName().charAt(i);
                }
                t1.add(temp);
                temp="";
                for (int i=stub+1;i<b.getName().length();i++){
                    temp = temp+b.getName().charAt(i);
                }
                t1.add(temp);
                t1.addAll(history);
                t1.add(objectName);
            }
        }
        for (int i=0;i<t1.size();i++){
            fullName = fullName+t1.get(i);
            if (i!=(t1.size()-1)){
                fullName=fullName+".";
            }
        }
        String subName;
        TBranchElement sub;
        List<TBranchElement> store = new ArrayList();
        for (int i=0;i<b.getBranches().size();i++){
            sub = (TBranchElement)b.getBranches().get(i);
            if (sub.getName().indexOf('[')>0){
                subName=sub.getName().substring(0,sub.getName().indexOf('['));
            }
            else {
                subName = sub.getName();
            }
            if (subName.equals(fullName)){
                store.add(sub);
            }
        }
        return store.get(0);
    }


    List<SRType> iterate(TStreamerInfo info,List<String> history,TBranchElement b){
        TStreamerElement streamerElement;
        List<SRType> temp = new ArrayList();
        TStreamerInfo sinfo;
        TBranchElement sub;
        for (int i=0;i<info.getElements().size();i++){
            streamerElement = (TStreamerElement)info.getElements().get(i);
            if (streamerElement.getType()>=0){
                int ttt=streamerElement.getType();
                if (ttt==0){
                    if (b.getType()==4 || b.getType()==3){
                        try{
                            sinfo = streamers.get(streamerElement.getName());
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            sinfo = null;
                        }
                        SRComposite srcomposite = new SRComposite(streamerElement.getName(),null,iterate(sinfo,history,b),true,false);
                        temp.add(srcomposite);
                    }
                    else {
                        sub = findBranch(streamerElement.getName(),history,b);
                        try {
                            sinfo = streamers.get(streamerElement.getName());
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            sinfo=null;
                        }
                        SRCompositeType srcompositetype = new SRCompositeType();
                        temp.add(synthesizeStreamerInfo(sub,sinfo,streamerElement,srcompositetype,true));
                    }
                }
                else if (ttt < 61 || ttt ==500){
                    sub = findBranch(streamerElement.getName(),history,b);
                    SRCompositeType srcompositetype = new SRCompositeType();
                    temp.add(synthesizeStreamerElement(sub,streamerElement,srcompositetype));
                }
                else {
                    try {
                        sinfo = streamers.get(formatNameForPointer(streamerElement.getTypeName()));
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        sinfo = null;
                    }
                    if (sinfo == null){
                        SRUnknown srunknown = new SRUnknown(streamerElement.getName());
                        temp.add(srunknown);
                    }
                    else {
                        try {
                            List<String> t1 = new ArrayList();
                            t1.addAll(history);
                            t1.add(streamerElement.getName());
                            SRComposite srcomposite = new SRComposite(streamerElement.getName(),null,iterate(sinfo,t1,b),true,false);
                            temp.add(srcomposite);
                        }
                        catch (Throwable ex){
                            sub = findBranch(streamerElement.getName(),history,b);
                            SRCompositeType srcompositetype = new SRCompositeType();
                            temp.add(synthesizeStreamerInfo(sub,sinfo,streamerElement,srcompositetype,false));
                        }
                    }
                }
            }
        }
        return temp;
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

package com.bless.KnowledgeUtils;

/**
 * Created by wangxi on 18/7/18.
 */
public class Tools {
    public static final String PHENOTYPE_SYMTOM = "症状";
    public static final String PHENOTYPE_TEST = "化验";

    public static String strToSymbol(String sybolStr){
       if (sybolStr.equals("且")){
           return " && ";
       }

        if (sybolStr.equals("或")){
            return " || ";
        }
        return  "";
    }
}

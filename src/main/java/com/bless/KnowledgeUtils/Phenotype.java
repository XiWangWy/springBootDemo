package com.bless.KnowledgeUtils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Created by wangxi on 18/7/17.
 */
@Data
public class Phenotype {

    //表型名字
    private String name;

    //表类型
    private String type;

    //程度
    private String degree;

    //修饰
    private String decorate;

    //数值
    private String value;

    //单位
    private String unit;

    //判断符号
    private String symbol;

    //获取组装形式的单个value
    public String getSpName(){
        return "(" + name + ")";
    }

    public String getSpDegree(){
        if (degree.equals("")){
            return "";
        }
        return "[" + degree + "]";
    }

    public String getSpDecorate(){
        if (decorate.equals("")){
            return "";
        }
        return  "[" + decorate + "]";
    }

    public String getSpValue(){
        if (value.equals("")){
            return "";
        }
        return "(" + value + ")";
    }

    public String getSpUnit(){
        if (unit.equals("")){
            return "";
        }
        return "[" + unit + "]";
    }

    /**
     * 获取每个小句子的特殊组合字符串
     * @return
     */
    public String getSpPhenotype(){
        String sentenceStr = "";
        switch (type) {
            case Tools.PHENOTYPE_SYMTOM:
                if (isOnlyName()){
                    sentenceStr = getSpName() + " " + getSpDecorate() + " " + getSpDegree();
                }else {
                    sentenceStr = "(" + getSpName() + " " + getSpDecorate() + " " + getSpDegree() + ")";
                }

                break;
            case Tools.PHENOTYPE_TEST:
                if (isOnlyName()){
                    sentenceStr = getSpName() + " " + getSymbol() + " " + getSpValue() + " " + getSpUnit();
                }else {
                    sentenceStr = "(" + getSpName() + " " + getSymbol() + " " + getSpValue() + " " + getSpUnit() + ")";
                }

                break;

        }
    return sentenceStr;
    }

    /**
     * 获取原文
     * @return
     */
    public String getSourceStr(){
        String sentenceStr = "";
        switch (type) {
            case Tools.PHENOTYPE_SYMTOM:
                sentenceStr = getName() + " " + getDecorate() + " " + getDegree();
                break;
            case Tools.PHENOTYPE_TEST:
                sentenceStr = getName() + " " + getSymbol() + " " + getValue() + " " + getUnit();
                break;

        }
        return sentenceStr;
    }



    public Phenotype(JSONObject pheno){
        this.setName(pheno.getString("name"));
        this.setType(pheno.getString("type"));
        this.setDegree(pheno.getString("degree"));
        this.setDecorate(pheno.getString("decorate"));
        this.setValue(pheno.getString("value"));
        this.setUnit(pheno.getString("unit"));
        this.setSymbol(pheno.getString("symbol"));
    }

    public Phenotype(){

    }

    public JSONObject getExportJSON(){
        JSONObject object = new JSONObject();
        object.put("表型名称",name);
        object.put("程度",degree);
        object.put("修饰",decorate);
        object.put("数值",value);
        object.put("单位",unit);
        object.put("判断符号",symbol);
        object.put("表型类型",type);
        return object;
    }

    public boolean isOnlyName(){
        if (!decorate.equals("") || !degree.equals("") || !value.equals("")){
            return false;
        }
        return true;
    }

}

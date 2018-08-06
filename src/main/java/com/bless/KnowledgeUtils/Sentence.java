package com.bless.KnowledgeUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;

/**
 * Created by wangxi on 18/7/17.
 */
@Data
public class Sentence {

    //原文
    private String src = "";

    //生成目标原文
    private String targetSrc = "";

    //多个症状或者多个化验数值
    private ArrayList<Phenotype> phenotypes;

    //中间间隔的操作符
    private ArrayList<String> symbols;

    //获取组装好的每个句子
    public String getSpTargetSrc(){
        if (phenotypes != null){
            if (phenotypes.size() == 1){
                targetSrc =  phenotypes.get(0).getSpPhenotype();
            }else {
                int indexCount = 0;
                for (Phenotype phenotype:phenotypes){
                    if (indexCount < symbols.size()){
                        targetSrc +=  phenotype.getSpPhenotype() + Tools.strToSymbol(symbols.get(indexCount));
                        indexCount++;
                    }else {
                        targetSrc += phenotype.getSpPhenotype();
                    }
                }

                targetSrc = "(" + targetSrc + ")";
            }
        }

        return targetSrc;
    }

    /**
     * 组装每个句子应该返回的json
     * @return
     */
    public JSONObject getExportJSON(){
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        for (Phenotype sen:phenotypes) {
            array.add(sen.getExportJSON());
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(symbols);

        object.put("表型",array);
        object.put("表型的关系",jsonArray);
        return object;
    }

    /**
     * 获取每个句子的组装原文
     * @return
     */
    public String getSpSource(String key){
        String sentence = "";
        int indexCount = 0;
        for (Phenotype phenotype:phenotypes){
            if (indexCount < symbols.size()){
                sentence += phenotype.getSourceStr() + " " + symbols.get(indexCount) + " ";
                indexCount++;
            }else {
                sentence += phenotype.getSourceStr();
            }
        }

        return key + "、" + sentence;
    }

}

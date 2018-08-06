package com.bless.KnowledgeController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bless.KnowledgeUtils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by wangxi on 18/7/17.
 */
@Api(value = "KnowlegdeController",description = "KnowlegdeController")
@Slf4j
@RestController
@RequestMapping("/")
public class KnowlegdeController {

    @Autowired
    private KnowlegdeEntityRepository knowlegdeEntityRepository;

    @Autowired
    private KnowlegdeTypeRepository knowlegdeTypeRepository;

//    @ApiOperation(value = "测试接口1",notes = "测试接口介绍")
//    @ApiImplicitParam(name = "user",value = "用户实体User",required = true,dataType = "User")
    @RequestMapping(value = "test",method = {RequestMethod.POST})
    public KnowlegdeEntity test(@RequestBody JSONObject object){
        log.info("~~~~~~~成功~~~~~~");

        String id = object.getString("id");
        KnowlegdeEntity knowlegdeEntity = new KnowlegdeEntity();
        String diag  = object.getString("诊断");
        JSONObject normal = object.getJSONObject("一般表型");
        Set<Map.Entry<String, Object>> entrySet = normal.entrySet();
        Iterator<Map.Entry<String, Object>> iter = entrySet.iterator();

        JSONObject reasons = new JSONObject();
        JSONObject save = new JSONObject();
        String If = "";
        String sourece = "";
        int indexCount = 0;

        ArrayList<Sentence> sents = new ArrayList<>();
        while(iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            String str = entry.getKey();
            Sentence sentence = new Sentence();

            JSONObject object1 = (JSONObject) entry.getValue();
            JSONArray symbols = object1.getJSONArray("表型的关系");
            JSONArray phenotypes = object1.getJSONArray("表型");

            List<String> list = symbols.toJavaList(String.class);
            sentence.setSymbols(new ArrayList<>(list));

            ArrayList<Phenotype> phenotypeList = new ArrayList<>();
            for (Object phe:phenotypes) {
                JSONObject pheno = (JSONObject) phe;
                Phenotype phenotype = new Phenotype(pheno);
                phenotypeList.add(phenotype);
            }
            sentence.setPhenotypes(phenotypeList);
            sents.add(sentence);
            save.put(str,sentence.getExportJSON());

            sourece += sentence.getSpSource(str);

            if (indexCount < normal.size() - 1 && normal.size() != 1) {
                If += sentence.getSpTargetSrc() + " && ";
            }else {
                If += sentence.getSpTargetSrc();
            }
            indexCount++;
        }
        reasons.put("一般表型",save);
        reasons.put("严重时",new JSONObject());
        reasons.put("后期",new JSONObject());
        reasons.put("可能",new JSONObject());
        knowlegdeEntity.setIf(If);
        knowlegdeEntity.setThen(diag);
        knowlegdeEntity.setReasons(reasons);
        knowlegdeEntity.setTreatment(new JSONObject());
        knowlegdeEntity.setSource(sourece);
        if (id != null && !id.equals("")){
            knowlegdeEntity.setId(id);
        }

        return knowlegdeEntityRepository.save(knowlegdeEntity);
    }

    @ApiOperation(value = "查询文本类型",notes = "查看当前输入文本内容是属于何种类型")
    @RequestMapping(value = "prompt",method = {RequestMethod.POST})
    public JSONObject prompt(@RequestBody KnowlegdeType knowlegdeType){
        JSONObject jsonObject = new JSONObject();
        KnowlegdeType tmp = knowlegdeTypeRepository.findByWord(knowlegdeType.getWord());
        if (tmp == null){
            jsonObject.put("isSuccess",false);
            jsonObject.put("type","未知");
            return jsonObject;
        }else {
            jsonObject.put("isSuccess",true);
            jsonObject.put("type",tmp.getType());
        }
        return jsonObject;
    }

    @ApiOperation(value = "插入对应类型的知识",notes = "将对应的症状化验文本插入数据库")
    @ApiImplicitParam(name = "knowlegdeType",value = "知识文本",required = true,dataType = "KnowlegdeType")
    @RequestMapping(value = "insertType",method = {RequestMethod.POST})
    public JSONObject insertType(@RequestBody KnowlegdeType knowlegdeType){
        JSONObject jsonObject = new JSONObject();
        KnowlegdeType tmp = knowlegdeTypeRepository.findByWord(knowlegdeType.getWord());
        if (tmp == null){
            knowlegdeTypeRepository.save(knowlegdeType);
            jsonObject.put("msg","数据插入成功！");
            return jsonObject;
        }else {
            jsonObject.put("msg","数据已经存在！");
            return jsonObject;
        }

    }

}

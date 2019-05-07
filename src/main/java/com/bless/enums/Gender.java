package com.bless.enums;


/**
 * Created by wangxi on 2019/1/24.
 */
public enum  Gender{
    male(2,"女"),
    famale(4,"男"),
    unknow(6,"未知");

    private Integer key;
    private String desc;

    public Integer getKey(){
        return this.key;
    }

    public String getDesc(){
        return this.desc;
    }

    Gender(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

//    Gender(Integer key, String desc) {
//        this.key = key;
//        this.desc = desc;
//        try{
//          Field fieldName =  getClass().getSuperclass().getDeclaredField("ordinal");
//          fieldName.setAccessible(true);
//          fieldName.set(this,key);
//          fieldName.setAccessible(false);
//
//            Field fieldName1 =  getClass().getSuperclass().getDeclaredField("desc");
//            fieldName1.setAccessible(true);
//            fieldName1.set(this,desc);
//            fieldName1.setAccessible(false);
//        }catch (Exception e){
//
//        }
//    }
}

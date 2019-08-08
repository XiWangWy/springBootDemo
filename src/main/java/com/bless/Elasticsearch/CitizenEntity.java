package com.bless.Elasticsearch;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * Created by wangxi on 2019/7/17.
 */
@Data
@NoArgsConstructor
public class CitizenEntity extends BaseEntity{
    @ESMappingField(type = DataType.KeyWord)
    private String name;
    @ESMappingField(type = DataType.Long)
    private Long idNo;
    @ESMappingField(type = DataType.Long)
    private Set<Long> tags;
    @ESMappingField(type = DataType.Nested)
    private List<CitizenChild> children;

     @Data
     public static class CitizenChild {
        @ESMappingField(type = DataType.KeyWord)
        private String childName;
        @ESMappingField(type = DataType.Integer)
        private Integer age;

         @Override
         public String toString() {
             return "CitizenChild{" +
                     "childName='" + childName + '\'' +
                     ", age=" + age +
                     '}';
         }

     }

    @Override
    public String toString() {
        return "CitizenEntity{" +
                "name='" + name + '\'' +
                ", idNo=" + idNo +
                ", tags=" + tags +
                ", children=" + children.toString() +
                '}';
    }
}

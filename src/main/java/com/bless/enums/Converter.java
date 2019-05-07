package com.bless.enums;


import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by wangxi on 2019/1/24.
 */
public class Converter implements AttributeConverter<Gender, Integer> {


    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        return gender.getKey();
    }

    @Override
    public Gender convertToEntityAttribute(Integer dbData) {
        Gender[] genders = Gender.values();
        Optional<Gender> gender = Arrays.stream(genders).filter(gender1 -> gender1.getKey().equals(dbData)).findAny();
        if (gender.get() != null) return gender.get();
        return null;
    }
}

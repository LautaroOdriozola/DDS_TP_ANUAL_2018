package com.tpdds.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BoolToBit implements AttributeConverter<Boolean, Integer> {

    public Integer convertToDatabaseColumn(Boolean num) {
        return num ? 1 : 0;
    }

    public Boolean convertToEntityAttribute(Integer num) {
        return num>0;
    }

}

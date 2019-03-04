package com.tpdds.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Converter
public class BigToDouble implements AttributeConverter<BigDecimal, Double> {

    public Double convertToDatabaseColumn(BigDecimal num) {
        return num.doubleValue();
    }

    public BigDecimal convertToEntityAttribute(Double num) {
        return new BigDecimal(num);
    }

}

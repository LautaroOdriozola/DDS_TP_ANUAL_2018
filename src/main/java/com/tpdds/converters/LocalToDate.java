package com.tpdds.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

@Converter
public class LocalToDate implements AttributeConverter<LocalDate, Date> {

    public Date convertToDatabaseColumn(LocalDate date) {

        int year = date.getYear();
        Month month = date.getMonth();
        int monthNumber = month.getValue();
        int day = date.getDayOfMonth();

        return new Date(day, monthNumber, year);
    }

    public LocalDate convertToEntityAttribute(Date date) {

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        return LocalDate.of(year, month, day);
    }

}

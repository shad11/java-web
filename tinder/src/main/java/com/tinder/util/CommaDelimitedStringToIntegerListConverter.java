package com.tinder.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;

public class CommaDelimitedStringToIntegerListConverter implements AttributeConverter<List<Integer>, String> {
    // Convert List<Integer> to a comma-delimited String
    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }

        return attribute.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    // Convert a comma-delimited String to List<Integer>
    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        return Arrays.stream(dbData.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}

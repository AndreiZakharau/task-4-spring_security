package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class SortParamsContext {
    private final List<String> sortColumns;
    private final List<String> orderTypes;


    public List<String> getSortColumns() {
        return sortColumns;
    }

    public List<String> getOrderTypes() {
        return orderTypes;
    }
}

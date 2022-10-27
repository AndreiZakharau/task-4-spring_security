package com.epam.esm.mapper;

public interface Mapper <F, T> {

    T mapFrom(F object);
}

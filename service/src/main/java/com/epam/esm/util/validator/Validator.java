package com.epam.esm.util.validator;

public interface Validator<T>{

    boolean isValid(T t);
}

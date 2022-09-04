package com.appkodar.demos.quarkus.validators;

public interface Validator<T>
{
    boolean validate(T obj);
}

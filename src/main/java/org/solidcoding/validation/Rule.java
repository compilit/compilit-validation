package org.solidcoding.validation;

public interface Rule<T> {

  boolean validate(T value);

}

package org.solidcoding.validation;

public interface ChainingPredicate<T, R> {

  R and(T second);

}

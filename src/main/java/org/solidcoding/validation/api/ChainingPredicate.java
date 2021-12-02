package org.solidcoding.validation.api;

public interface ChainingPredicate<T, R> {

    R and(T second);

}

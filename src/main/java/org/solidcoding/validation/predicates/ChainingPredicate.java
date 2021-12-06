package org.solidcoding.validation.predicates;

public interface ChainingPredicate<T, R> {

    R and(T second);

}

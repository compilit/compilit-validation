package org.solidcoding.validation.predicates;

public interface PredicateConnector<T, R> {

  R and(T value);
}

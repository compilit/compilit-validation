package org.solidcoding.validation.predicates;

public interface ChainingPredicate<T, R> {

    /**
     * @param second second of the constraints. Can be either the high constraint or the low constraint.
     * @return a Predicate to continue adding rules.
     */
    R and(T second);

}

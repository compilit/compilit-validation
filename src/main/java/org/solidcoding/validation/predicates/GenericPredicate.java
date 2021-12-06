package org.solidcoding.validation.predicates;

import java.util.function.Predicate;

public interface GenericPredicate<T> extends Predicate<T> {

    /**
     * @param rule the custom predicate to test against properties of T.
     * @return ObjectPredicate to continue adding rules.
     */
    Predicate<T> where(Predicate<T> rule);

}

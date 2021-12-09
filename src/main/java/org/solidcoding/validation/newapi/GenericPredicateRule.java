package org.solidcoding.validation.newapi;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface GenericPredicateRule<T> {

    boolean isPredicate();

    boolean isBiPredicate();

    default Predicate<T> getPredicate() {
        return x -> false;
    }
    default BiPredicate<T, Object> getBiPredicate() {
        return (x, y) -> false;
    }

    default boolean test(T value) {
        return getPredicate().test(value);
    }

    default boolean test(T value, Object argument) {
        return getBiPredicate().test(value, argument);
    }

    /**
     * @param predicate the custom predicate to test against properties of T.
     * @return Predicate to continue adding rules.
     */
    default Predicate<T> where(Predicate<T> predicate) {
        return predicate.and(this::test);
    }

    /**
     * @param predicate the custom predicate to test against properties of T.
     * @return Predicate to continue adding rules.
     */
    default BiPredicate<T, Object> where(BiPredicate<T, Object> predicate) {
        return predicate.and(this::test);
    }
}

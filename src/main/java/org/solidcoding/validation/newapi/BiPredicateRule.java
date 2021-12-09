package org.solidcoding.validation.newapi;

import java.util.function.BiPredicate;

public class BiPredicateRule<T> implements GenericPredicateRule<T>, BiPredicate<T, Object> {

    private final BiPredicate<T, Object> predicate;

    public BiPredicateRule(BiPredicate<T, Object> predicate) {
        this.predicate = predicate;
    }

    @Override
    public BiPredicate<T, Object> getBiPredicate() {
        return predicate;
    }

    @Override
    public boolean test(T value, Object argument) {
        return GenericPredicateRule.super.test(value, argument);
    }

    @Override
    public boolean isPredicate() {
        return false;
    }

    @Override
    public boolean isBiPredicate() {
        return true;
    }

}

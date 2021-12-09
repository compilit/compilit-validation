package org.solidcoding.validation.newapi;

import java.util.function.Predicate;

public class PredicateRule<T> implements GenericPredicateRule<T>, Predicate<T> {

    private final Predicate<T> predicate;

    public PredicateRule(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public Predicate<T> getPredicate() {
        return predicate;
    }

    @Override
    public boolean test(T value) {
        return GenericPredicateRule.super.test(value);
    }

    @Override
    public boolean isPredicate() {
        return true;
    }

    @Override
    public boolean isBiPredicate() {
        return false;
    }

}

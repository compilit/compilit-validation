package org.solidcoding.validation.predicates;

import org.solidcoding.validation.api.ChainingPredicate;

import java.util.function.Predicate;

final class DecimalNumberConstraintPredicate implements ChainingPredicate<Double, Predicate<Double>> {

    private final PredicateContainer<Double> originalPredicate;
    private final double first;

    DecimalNumberConstraintPredicate(Double first, PredicateContainer<Double> originalPredicate) {
        this.first = first;
        this.originalPredicate = originalPredicate;
    }

    /**
     * @param second the second of the (inclusive) constraints. Can be either the high constraint or the low
     *               constraint.
     * @return DecimalNumberPredicate to continue adding rules.
     */
    public Predicate<Double> and(Double second) {
        if (second > first) {
            originalPredicate.addPredicate(x -> x <= second && x >= first);
        } else {
            originalPredicate.addPredicate(x -> x <= first && x >= second);
        }
        return originalPredicate;
    }

}

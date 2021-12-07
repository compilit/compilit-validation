package org.solidcoding.validation.predicates;

import java.util.function.Predicate;

final class DecimalNumberConstraintPredicate implements ChainingPredicate<Double, Predicate<Double>> {

    private final ObjectPredicateBuilder<Double> originalPredicate;
    private final double first;

    DecimalNumberConstraintPredicate(Double first) {
        this.first = first;
        this.originalPredicate = new ObjectPredicateBuilder<>();
    }

    /**
     * @param second the second of the (inclusive) constraints. Can be either the high constraint or the low
     *               constraint.
     * @return Predicate to continue adding rules.
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

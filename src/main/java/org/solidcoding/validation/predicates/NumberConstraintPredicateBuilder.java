package org.solidcoding.validation.predicates;

import org.solidcoding.validation.api.ChainingPredicate;

import java.util.function.Predicate;

final class NumberConstraintPredicateBuilder implements ChainingPredicate<Integer, Predicate<Integer>> {

    private final PredicateContainer<Integer> originalPredicate;
    private final int first;

    NumberConstraintPredicateBuilder(Integer first, PredicateContainer<Integer> originalPredicate) {
        this.first = first;
        this.originalPredicate = originalPredicate;
    }

    /**
     * @param second second of the constraints. Can be either the high constraint or the low
     *               constraint.
     * @return IntegerPredicate to continue adding rules.
     */
    public Predicate<Integer> and(Integer second) {
        if (second > first) {
            originalPredicate.addPredicate(x -> x <= second && x >= first);
        } else {
            originalPredicate.addPredicate(x -> x <= first && x >= second);
        }
        return originalPredicate;
    }

}

package org.solidcoding.validation.predicates;

import org.solidcoding.validation.api.ChainingPredicate;

import java.util.function.Predicate;

final class StringLengthConstraintPredicateBuilder implements ChainingPredicate<Integer, Predicate<String>> {

    private final PredicateContainer<String> originalPredicate;
    private final int first;

    StringLengthConstraintPredicateBuilder(Integer first, PredicateContainer<String> originalPredicate) {
        this.first = first;
        this.originalPredicate = originalPredicate;
    }

    /**
     * @param second second of the constraints. Can be either the high constraint or the low constraint.
     * @return StringPredicate to continue adding rules.
     */
    public Predicate<String> and(Integer second) {
        if (second > first) {
            originalPredicate.addPredicate(x -> x.length() <= second && x.length() >= first);
        } else {
            originalPredicate.addPredicate(x -> x.length() <= first && x.length() >= second);
        }
        return originalPredicate;
    }

}

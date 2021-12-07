package org.solidcoding.validation.predicates;

import java.util.function.Predicate;

final class StringLengthConstraintPredicateBuilder implements ChainingPredicate<Integer, Predicate<String>> {

    private final ObjectPredicateBuilder<String> originalPredicate;
    private final int first;

    StringLengthConstraintPredicateBuilder(Integer first) {
        this.first = first;
        this.originalPredicate = new ObjectPredicateBuilder<>();
    }

    public Predicate<String> and(Integer second) {
        if (second > first) {
            originalPredicate.addPredicate(x -> x.length() <= second && x.length() >= first);
        } else {
            originalPredicate.addPredicate(x -> x.length() <= first && x.length() >= second);
        }
        return originalPredicate;
    }

}

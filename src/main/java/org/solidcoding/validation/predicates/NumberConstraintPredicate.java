package org.solidcoding.validation.predicates;

import org.solidcoding.validation.api.ChainingPredicate;

public final class NumberConstraintPredicate implements ChainingPredicate<Integer, NumberPredicate> {

    private final NumberPredicate originalPredicate;
    private final int first;

    NumberConstraintPredicate(Integer first, NumberPredicate originalPredicate) {
        this.first = first;
        this.originalPredicate = originalPredicate;
    }

    /**
     * @param second second of the constraints. Can be either the high constraint or the low
     *               constraint.
     * @return IntegerPredicate to continue adding rules.
     */
    public NumberPredicate and(Integer second) {
        if (second > first) {
            originalPredicate.rules.add(x -> x <= second && x >= first);
        } else {
            originalPredicate.rules.add(x -> x <= first && x >= second);
        }
        return originalPredicate;
    }

}

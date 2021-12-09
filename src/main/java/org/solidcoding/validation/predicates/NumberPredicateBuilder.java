package org.solidcoding.validation.predicates;

import java.util.function.Predicate;

public final class NumberPredicateBuilder extends ObjectPredicateBuilder<Integer> {

    private NumberPredicateBuilder(Predicate<Integer> predicate) {
        super(predicate);
    }

    /**
     * Checks whether the actual value is present.*
     * @return Predicate to continue adding rules.
     */
    public static Predicate<Integer> isNotNull() {
        return ObjectPredicateBuilder.isNotNull();
    }

    /**
     * Check if the actual value is equal to the given one.
     *
     * @param value the exact expected value.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<Integer> isEqualTo(int value) {
        return ObjectPredicateBuilder.isEqualTo(value);
    }

    /**
     * @param amountOfDigits the exact amount of digits of the Integer.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<Integer> hasAmountOfDigits(int amountOfDigits) {
        return new NumberPredicateBuilder(x -> String.valueOf(x).length() == amountOfDigits);
    }

    /**
     * @param first the first (inclusive) constraint. Can be either the high constraint or the low
     *                             constraint.
     * @return a ChainingPredicate to add the second constraint.
     */
    public static ContinuingPredicate<Integer, Predicate<Integer>> isBetween(int first) {
        return new NumberConstraintPredicateBuilder(first, new ObjectPredicateBuilder<>());
    }

    /**
     * Checks whether the given Integers are present anywhere in the value.
     *
     * @param value  the exact value that needs to be present in the toString of the original value.
     * @param values the optional exact values that needs to be present in the toString of the original value.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<Integer> contains(Integer value, Integer... values) {
        return ObjectPredicateBuilder.contains(value, (Object[]) values);
    }

    /**
     * Checks whether the given Integers are not present anywhere in the value.
     *
     * @param value  the exact value that may not be present in the toString of the original value.
     * @param values the optional exact values that may not be present in the toString of the original value.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<Integer> doesNotContain(Integer value, Integer... values) {
        return ObjectPredicateBuilder.doesNotContain(value, (Object[]) values);
    }

}

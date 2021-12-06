package org.solidcoding.validation.predicates;

import org.solidcoding.validation.api.ChainingPredicate;

import java.util.function.Predicate;

public final class NumberPredicateBuilder extends PredicateContainer<Integer> implements NumberPredicate {

    private NumberPredicateBuilder() {
    }

    private NumberPredicateBuilder(Predicate<Integer> rule) {
        addPredicate(rule);
    }

    /**
     * @return IntegerPredicate to continue adding rules.
     */
    public static NumberPredicate is() {
        return new NumberPredicateBuilder();
    }


    /**
     * @param value the exact expected value.
     * @return IntegerPredicate to continue adding rules.
     */
    public static Predicate<Integer> is(int value) {
        return new NumberPredicateBuilder(x -> x == value);
    }

    /**
     * @param amountOfDigits the exact amount of digits of the Integer.
     * @return IntegerPredicate to continue adding rules.
     */
    public static Predicate<Integer> hasAmountOfDigits(int amountOfDigits) {
        return new NumberPredicateBuilder(x -> String.valueOf(x).length() == amountOfDigits);
    }

    /**
     * Checks whether the given Integers are present anywhere in the value.
     *
     * @param value  the exact value that needs to be present in the toString of the original value.
     * @param values the optional exact values that needs to be present in the toString of the original value.
     * @return NumberPredicate to continue adding rules.
     */
    public static Predicate<Integer> contains(Integer value, Integer... values) {
        return PredicateContainer.contains(value, (Object[]) values);
    }

    /**
     * Checks whether the given Integers are not present anywhere in the value.
     *
     * @param value  the exact value that may not be present in the toString of the original value.
     * @param values the optional exact values that may not be present in the toString of the original value.
     * @return NumberPredicate to continue adding rules.
     */
    public static Predicate<Integer> doesNotContain(Integer value, Integer... values) {
        return PredicateContainer.doesNotContain(value, (Object[]) values);
    }

    @Override
    public ChainingPredicate<Integer, Predicate<Integer>> between(int first) {
        return new NumberConstraintPredicateBuilder(first, this);
    }
}

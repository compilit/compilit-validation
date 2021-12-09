package org.solidcoding.validation.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class DecimalNumberPredicateBuilder extends ObjectPredicateBuilder<Double> {

    private DecimalNumberPredicateBuilder(List<Predicate<Double>> predicates) {
        super(predicates);
    }

    /**
     * Checks whether the actual value is present.*
     *
     * @return Predicate to continue adding rules.
     */
    public static Predicate<Double> isNotNull() {
        return ObjectPredicateBuilder.isNotNull();
    }

    /**
     * @param first the first (inclusive) constraint. Can be either the high constraint or the low
     *              constraint.
     * @return a ChainingPredicate to add the second constraint.
     */
    public static ContinuingPredicate<Double, Predicate<Double>> isBetween(double first) {
        return new DecimalNumberConstraintPredicate(first);
    }

    /**
     * Check if the actual value is equal to the given one.
     *
     * @param value the exact expected value.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<Double> isEqualTo(double value) {
        return ObjectPredicateBuilder.isEqualTo(value);
    }

    /**
     * Checks whether the given Integers are present anywhere in the value.
     *
     * @param value  the exact value that needs to be present in the toString of the original value.
     * @param values the optional exact values that needs to be present in the toString of the original value.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<Double> contains(Integer value, Integer... values) {
        var rules = new ArrayList<Predicate<Double>>();
        rules.add(x -> x.toString().contains(value.toString()));
        for (var c : values) {
            rules.add(x -> x.toString().contains(c.toString()));
        }
        return new DecimalNumberPredicateBuilder(rules);
    }

    /**
     * Checks whether the given Integers are not present anywhere in the value.
     *
     * @param value  the exact value that may not be present in the toString of the original value.
     * @param values the optional exact values that may not be present in the toString of the original value.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<Double> doesNotContain(Integer value, Integer... values) {
        var rules = new ArrayList<Predicate<Double>>();
        rules.add(x -> !x.toString().contains(value.toString()));
        for (var c : values) {
            rules.add(x -> !x.toString().contains(c.toString()));
        }
        return new DecimalNumberPredicateBuilder(rules);
    }


}

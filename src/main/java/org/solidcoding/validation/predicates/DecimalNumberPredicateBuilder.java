package org.solidcoding.validation.predicates;

import org.solidcoding.validation.api.ChainingPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class DecimalNumberPredicateBuilder extends PredicateContainer<Double> implements DecimalNumberPredicate {

    private DecimalNumberPredicateBuilder() {
    }

    private DecimalNumberPredicateBuilder(Predicate<Double> rule) {
        addPredicate(rule);
    }

    private DecimalNumberPredicateBuilder(List<Predicate<Double>> rules) {
        rules.forEach(this::addPredicate);
    }

    /**
     * Checks whether the actual value is present.*
     * @return Predicate to continue adding rules.
     */
    public static Predicate<Double> isNotNull() {
        return ObjectPredicateBuilder.isNotNull();
    }

    /**
     * @return DoublePredicate to continue adding rules.
     */
    public static DecimalNumberPredicate is() {
        return new DecimalNumberPredicateBuilder();
    }

    /**
     * @param value the exact expected value.
     * @return Predicate to continue adding rules.
     */
    public static DecimalNumberPredicate is(double value) {
        return new DecimalNumberPredicateBuilder(x -> x == value);
    }

    /**
     * @param rule the custom predicate to test against the Double.
     * @return Predicate to continue adding rules.
     */
    public static DecimalNumberPredicate is(Predicate<Double> rule) {
        return new DecimalNumberPredicateBuilder(rule);
    }

    /**
     * Checks whether the given Integers are present anywhere in the value.
     *
     * @param value  the exact value that needs to be present in the toString of the original value.
     * @param values the optional exact values that needs to be present in the toString of the original value.
     * @return DecimalNumberPredicate to continue adding rules.
     */
    public static DecimalNumberPredicate contains(Integer value, Integer... values) {
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
     * @return DecimalNumberPredicate to continue adding rules.
     */
    public static DecimalNumberPredicate doesNotContain(Integer value, Integer... values) {
        var rules = new ArrayList<Predicate<Double>>();
        rules.add(x -> !x.toString().contains(value.toString()));
        for (var c : values) {
            rules.add(x -> !x.toString().contains(c.toString()));
        }
        return new DecimalNumberPredicateBuilder(rules);
    }

    public ChainingPredicate<Double, Predicate<Double>> between(double first) {
        return new DecimalNumberConstraintPredicate(first, this);
    }

}

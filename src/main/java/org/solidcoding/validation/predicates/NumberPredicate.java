package org.solidcoding.validation.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class NumberPredicate implements Predicate<Integer> {

    List<Predicate<Integer>> rules = new ArrayList<>();

    private NumberPredicate() {
    }

    private NumberPredicate(Predicate<Integer> rule) {
        this.rules.add(rule);
    }

    /**
     * @return IntegerPredicate to continue adding rules.
     */
    public static NumberPredicate beANumber() {
        return new NumberPredicate();
    }

    /**
     * @param value the exact expected value.
     * @return IntegerPredicate to continue adding rules.
     */
    public static NumberPredicate beANumber(int value) {
        return new NumberPredicate(x -> x == value);
    }

    /**
     * @param rule the custom predicate to test against the Integer.
     * @return IntegerPredicate to continue adding rules.
     */
    public static NumberPredicate beANumber(Predicate<Integer> rule) {
        return new NumberPredicate(rule);
    }

    /**
     * @param amountOfDigits the exact amount of digits of the Integer.
     * @return IntegerPredicate to continue adding rules.
     */
    public static NumberPredicate beANumberOfLength(int amountOfDigits) {
        return beANumber(x -> String.valueOf(x).length() == amountOfDigits);
    }

    public NumberConstraintPredicate between(int first) {
        return new NumberConstraintPredicate(first, this);
    }

    /**
     * Determines if the given integers are present or a part of the actual Integer.
     *
     * @param integer  the integer that needs to be present in the Integer.
     * @param integers the optional integers that need to be present in the Integer.
     * @return IntegerPredicate to continue adding rules.
     */
    public NumberPredicate containing(int integer, int... integers) {
        rules.add(x -> String.valueOf(x).contains(String.valueOf(integer)));
        for (var i : integers) {
            rules.add(x -> String.valueOf(x).contains(String.valueOf(i)));
        }
        return this;
    }

    @Override
    public boolean test(Integer value) {
        return rules.stream().allMatch(x -> x.test(value));
    }


}

package org.solidcoding.validation.predicates;

import org.solidcoding.validation.api.ChainingPredicate;

import java.util.Objects;
import java.util.function.Predicate;

public final class StringPredicateBuilder extends PredicateContainer<String> {

    private StringPredicateBuilder() {
        super();
    }

    private StringPredicateBuilder(Predicate<String> rule) {
        super();
        addPredicate(rule);
    }

    /**
     * Checks whether the given charSequences are present anywhere in the value.
     *
     * @param value  the exact value that needs to be present in the original value.
     * @param values the optional exact values that needs to be present in the original value.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> contains(CharSequence value, CharSequence... values) {
       return contains(value, (Object[]) values);
    }

    /**
     * Checks whether the given charSequences are present anywhere in the value.
     *
     * @param value  the exact value that may not be present in the original value.
     * @param values the optional exact values that may not be present in the original value.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> doesNotContain(CharSequence value, CharSequence... values) {
        return doesNotContain(value, (Object[]) values);
    }

    /**
     * Check if the actual value is equal to the given one.
     *
     * @param value the exact expected value.
     * @return StringPredicate to continue adding rules.
     */
    public static Predicate<String> is(String value) {
        return new StringPredicateBuilder(is(x -> Objects.equals(x, value)));
    }

    /**
     * @param rule the custom predicate to test against the String.
     * @return StringPredicate to continue adding rules.
     */
    public static Predicate<String> is(Predicate<String> rule) {
        return new StringPredicateBuilder(rule);
    }

    /**
     * @param length the exact length of the String.
     * @return StringPredicate to continue adding rules.
     */
    public static ChainingPredicate<Integer, Predicate<String>> hasALengthBetween(int length) {
        return new StringLengthConstraintPredicateBuilder(length, new StringPredicateBuilder());
    }

    /**
     * @param length the exact length of the String.
     * @return StringPredicate to continue adding rules.
     */
    public static Predicate<String> hasALengthOf(int length) {
        return is(x -> x.length() == length);
    }

    /**
     * Adds a check if all characters are in fact digits.
     *
     * @return StringPredicate to continue adding rules.
     */
    public static Predicate<String> isNumeric() {
        return is(x -> x.chars().allMatch(Character::isDigit));
    }

    /**
     * Adds a check if any character is in fact not a digit.
     *
     * @return StringPredicate to continue adding rules.
     */
    public static Predicate<String> isNotNumeric() {
        return is(x -> x.chars().anyMatch(Character::isAlphabetic));
    }

    /**
     * Adds a check if all characters are in fact digits.
     *
     * @return StringPredicate to continue adding rules.
     */
    public static Predicate<String> isAlphabetic() {
        return is(x -> x.chars().allMatch(Character::isAlphabetic));
    }

    /**
     * Adds a check if any character is in fact not alphabetic.
     *
     * @return StringPredicate to continue adding rules.
     */
    public static Predicate<String> isNotAlphabetic() {
        return is(x -> x.chars().anyMatch(Character::isDigit));
    }

}

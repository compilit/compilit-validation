package org.solidcoding.validation.predicates;

import java.util.function.Predicate;

public final class StringPredicate extends ObjectPredicate<String> {

    private StringPredicate(final Predicate<String> predicate) {
        super(predicate);
    }

    /**
     * Checks whether the actual value is present.*
     *
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> isNotNull() {
        return ObjectPredicate.isNotNull();
    }

    /**
     * Checks whether the given charSequences are present anywhere in the value.
     *
     * @param value  the exact value that needs to be present in the original value.
     * @param values the optional exact values that needs to be present in the original value.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> contains(final CharSequence value, final CharSequence... values) {
        return contains(value, (Object[]) values);
    }

    /**
     * Checks whether the given charSequences are present anywhere in the value.
     *
     * @param value  the exact value that may not be present in the original value.
     * @param values the optional exact values that may not be present in the original value.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> doesNotContain(final CharSequence value, final CharSequence... values) {
        return doesNotContain(value, (Object[]) values);
    }

    /**
     * Check if the actual value is equal to the given one.
     *
     * @param value the exact expected value.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> isEqualTo(final String value) {
        return ObjectPredicate.isEqualTo(value);
    }

    /**
     * @param length the exact length of the String.
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> hasALengthOf(final int length) {
        return new StringPredicate(x -> x.length() == length);
    }

    /**
     * Adds a check if all characters are in fact digits.
     *
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> isNumeric() {
        return new StringPredicate(x -> x.chars().allMatch(Character::isDigit));
    }

    /**
     * Adds a check if any character is in fact not a digit.
     *
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> isNotNumeric() {
        return new StringPredicate(x -> x.chars().anyMatch(Character::isAlphabetic));
    }

    /**
     * Adds a check if all characters are in fact digits.
     *
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> isAlphabetic() {
        return new StringPredicate(x -> x.chars().allMatch(Character::isAlphabetic));
    }

    /**
     * Adds a check if any character is in fact not alphabetic.
     *
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> isNotAlphabetic() {
        return new StringPredicate(x -> x.chars().anyMatch(Character::isDigit));
    }

    /**
     * @param length the exact length of the String.
     * @return ChainingPredicate to continue adding rules.
     */
    public static ConstraintAppender<Integer, Predicate<String>> hasALengthBetween(final int length) {
        return new StringLengthConstraintAppender(length);
    }

}

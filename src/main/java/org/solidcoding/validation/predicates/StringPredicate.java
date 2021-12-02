package org.solidcoding.validation.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public final class StringPredicate implements Predicate<String> {

    List<Predicate<String>> rules = new ArrayList<>();

    StringPredicate() {
    }

    private StringPredicate(Predicate<String> rule) {
        this.rules.add(rule);
    }

    @SafeVarargs
    private StringPredicate(Predicate<String>... rules) {
        Objects.requireNonNull(rules);
        this.rules.addAll(List.of(rules));
    }

    /**
     * @return StringPredicate to continue adding rules.
     */
    public static StringPredicate beAString() {
        return new StringPredicate();
    }

    /**
     * @param value the exact expected value.
     * @return StringPredicate to continue adding rules.
     */
    public static StringPredicate beAString(String value) {
        return new StringPredicate(beAString(x -> Objects.equals(x, value)));
    }

    /**
     * @param rule the custom predicate to test against the String.
     * @return StringPredicate to continue adding rules.
     */
    public static StringPredicate beAString(Predicate<String> rule) {
        return new StringPredicate(rule);
    }

    /**
     * @param length the exact length of the String.
     * @return StringPredicate to continue adding rules.
     */
    public static StringPredicate beAStringWithLength(int length) {
        return beAString(x -> x.length() == length);
    }

    /**
     * Adds a check if all characters are in fact digits.
     *
     * @return StringPredicate to continue adding rules.
     */
    public static StringPredicate beNumeric() {
        return beAString(x -> x.chars().allMatch(Character::isDigit));
    }

    /**
     * Adds a check if all characters are in fact digits.
     *
     * @param amountOfDigits the expected amount of digits.
     * @return StringPredicate to continue adding rules.
     */
    public static StringPredicate beNumeric(int amountOfDigits) {
        return new StringPredicate(x -> x.chars().allMatch(Character::isDigit),
                x -> x.length() == amountOfDigits);
    }

    /**
     * Adds a check if all characters are in fact digits.
     *
     * @return StringPredicate to continue adding rules.
     */
    public static StringPredicate beAlphabetic() {
        return beAString(x -> x.chars().allMatch(Character::isAlphabetic));
    }

    /**
     * Adds a check if all characters are in fact letters.
     *
     * @param amountOfLetters the expected amount of letters.
     * @return StringPredicate to continue adding rules.
     */
    public static StringPredicate beAlphabetic(int amountOfLetters) {
        return new StringPredicate(x -> x.chars().allMatch(Character::isAlphabetic),
                x -> x.length() == amountOfLetters);
    }

    /**
     * @param charSequence  the character sequence that needs to be present in the String.
     * @param charSequences the optional extra character sequences that need to be present in the
     *                      String.
     * @return StringPredicate to continue adding rules.
     */
    public StringPredicate containing(CharSequence charSequence, CharSequence... charSequences) {
        rules.add(x -> x.contains(charSequence));
        for (var c : charSequences) {
            rules.add(x -> x.contains(c));
        }
        return this;
    }

    /**
     * @param first first of the constraints. Can be either the high constraint or the low
     *              constraint.
     * @return StringLengthPredicate to continue statement.
     */
    public StringLengthPredicate withLengthBetween(int first) {
        return new StringLengthPredicate(first, this);
    }

    /**
     * @param rule the custom predicate to test against the String.
     * @return StringLengthPredicate to continue adding rules.
     */
    public StringPredicate that(Predicate<String> rule) {
        rules.add(rule);
        return this;
    }

    /**
     * @param value check the given value against all specified rules
     * @return true when all checks pass.
     */
    @Override
    public boolean test(String value) {
        return rules.stream().allMatch(x -> x.test(value));
    }

}

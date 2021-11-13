package org.solidcoding.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class StringPredicate implements Predicate<String> {

  public List<Predicate<String>> rules = new ArrayList<>();

  private StringPredicate() {}

  private StringPredicate(Predicate<String> rule) {
    this.rules.add(rule);
  }

  @SafeVarargs
  private StringPredicate(Predicate<String>... rules) {
    Objects.requireNonNull(rules);
    this.rules.addAll(List.of(rules));
  }

  /**
   * @param characters the character sequence that needs to be present in the String.
   * @return StringPredicate to continue adding rules.
   */
  public StringPredicate containing(CharSequence characters) {
    return new StringPredicate(x -> x.contains(characters));
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
   * @return StringPredicate to continue adding rules.
   */
  public static StringPredicate beNumeric() {
    return beAString(x -> x.chars().allMatch(Character::isDigit));
  }

  /**
   * Adds a check if all characters are in fact digits.
   * @param amountOfDigits the expected amount of digits.
   * @return StringPredicate to continue adding rules.
   */
  public static StringPredicate beNumeric(int amountOfDigits) {
    return new StringPredicate(x -> x.chars().allMatch(Character::isDigit),
                               x -> x.length() == amountOfDigits);
  }

  /**
   * Adds a check if all characters are in fact digits.
   * @return StringPredicate to continue adding rules.
   */
  public static StringPredicate beAlphabetic() {
    return beAString(x -> x.chars().allMatch(Character::isAlphabetic));
  }

  /**
   * Adds a check if all characters are in fact letters.
   * @param amountOfLetters the expected amount of letters.
   * @return StringPredicate to continue adding rules.
   */
  public static StringPredicate beAlphabetic(int amountOfLetters) {
    return new StringPredicate(x -> x.chars().allMatch(Character::isAlphabetic),
                               x -> x.length() == amountOfLetters);
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

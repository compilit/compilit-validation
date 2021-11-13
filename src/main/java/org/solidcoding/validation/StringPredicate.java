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

  public StringPredicate containing(CharSequence characters) {
    return new StringPredicate(x -> x.contains(characters));
  }

  public static StringPredicate beAString() {
    return new StringPredicate();
  }

  public static StringPredicate beAString(String value) {
    return new StringPredicate(beAString(x -> Objects.equals(x, value)));
  }

  public static StringPredicate beAString(Predicate<String> rule) {
    return new StringPredicate(rule);
  }

  public static StringPredicate beAStringWithLength(int length) {
    return beAString(x -> x.length() == length);
  }

  public static StringPredicate beNumeric() {
    return beAString(x -> x.chars().allMatch(Character::isDigit));
  }

  public static StringPredicate beNumeric(int amountOfDigits) {
    return new StringPredicate(x -> x.chars().allMatch(Character::isDigit),
                               x -> x.length() == amountOfDigits);
  }

  public static StringPredicate beAlphabetic() {
    return beAString(x -> x.chars().allMatch(Character::isAlphabetic));
  }

  public static StringPredicate beAlphabetic(int amountOfLetters) {
    return new StringPredicate(x -> x.chars().allMatch(Character::isAlphabetic),
                               x -> x.length() == amountOfLetters);
  }

  @Override
  public boolean test(String value) {
    return rules.stream().allMatch(x -> x.test(value));
  }

}

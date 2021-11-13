package org.solidcoding.validation;

import java.util.function.Predicate;

public class IntegerPredicate implements Predicate<Integer> {

  public Predicate<Integer> rule;

  private IntegerPredicate() {}

  private IntegerPredicate(Predicate<Integer> rule) {
    this.rule = rule;
  }

  public IntegerPredicate between(int low, int high) {
    return new IntegerPredicate(x -> x >= low && x <= high);
  }

  public static IntegerPredicate beAnInteger() {
    return new IntegerPredicate();
  }

  public static IntegerPredicate beAnInteger(int value) {
    return new IntegerPredicate(x -> x == value);
  }

  public static IntegerPredicate beAnInteger(Predicate<Integer> rule) {
    return new IntegerPredicate(rule);
  }

  public static IntegerPredicate beAnIntegerOfLength(int amountOfDigits) {
    return beAnInteger(x -> String.valueOf(x).length() == amountOfDigits);
  }

  @Override
  public boolean test(Integer value) {
    return rule.test(value);
  }


}

package org.solidcoding.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

final class DecimalNumberPredicate implements Predicate<Double> {

  List<Predicate<Double>> rules = new ArrayList<>();

  private DecimalNumberPredicate() {}

  private DecimalNumberPredicate(Predicate<Double> rule) {
    this.rules.add(rule);
  }

  /**
   * @return DoublePredicate to continue adding rules.
   */
  public static DecimalNumberPredicate beADecimalNumber() {
    return new DecimalNumberPredicate();
  }

  /**
   * @param value the exact expected value.
   * @return DoublePredicate to continue adding rules.
   */
  public static DecimalNumberPredicate beADecimalNumber(double value) {
    return new DecimalNumberPredicate(x -> x == value);
  }

  /**
   * @param rule the custom predicate to test against the Double.
   * @return DoublePredicate to continue adding rules.
   */
  public static DecimalNumberPredicate beADecimalNumber(Predicate<Double> rule) {
    return new DecimalNumberPredicate(rule);
  }

  public DecimalNumberConstraintPredicate between(double first) {
    return new DecimalNumberConstraintPredicate(first, this);
  }

  /**
   * Determines if the given numbers are present or a part of the actual decimal number.
   *
   * @param number  the integer that needs to be present in the decimal number.
   * @param numbers the optional numbers that need to be present in the decimal number.
   * @return DecimalNumberPredicate to continue adding rules.
   */
  public DecimalNumberPredicate containing(int number, int... numbers) {
    rules.add(x -> String.valueOf(x).contains(String.valueOf(number)));
    for (var i : numbers) {
      rules.add(x -> String.valueOf(x).contains(String.valueOf(i)));
    }
    return this;
  }

  @Override
  public boolean test(Double value) {
    return rules.stream().allMatch(x -> x.test(value));
  }


}

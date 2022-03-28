package com.compilit.validation.predicates;

import java.util.function.Predicate;

public final class NumberPredicate extends ObjectPredicate<Integer> {

  private NumberPredicate(final Predicate<Integer> predicate) {
    super(predicate);
  }

  /**
   * Checks whether the actual value is present.
   *
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Integer> isNotNull() {
    return ObjectPredicate.isNotNull();
  }

  /**
   * Checks whether the actual value is not present.
   *
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Integer> isNull() {
    return ObjectPredicate.isNull();
  }

  /**
   * Check if the actual value is equal to the given one.
   *
   * @param value the exact expected value.
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Integer> isEqualTo(final int value) {
    return ObjectPredicate.isEqualTo(value);
  }

  /**
   * @param amountOfDigits the exact amount of digits of the Integer.
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Integer> hasAmountOfDigits(final int amountOfDigits) {
    return new NumberPredicate(x -> String.valueOf(x).length() == amountOfDigits);
  }

  /**
   * @param first the first (inclusive) constraint. Can be either the high constraint or the low
   *              constraint.
   * @return a ChainingPredicate to add the second constraint.
   */
  public static ConstraintAppender<Integer, Predicate<Integer>> isBetween(final int first) {
    return new NumberConstraintAppender(first);
  }

  /**
   * Checks whether the given Integers are present anywhere in the value.
   *
   * @param value  the exact value that needs to be present in the toString of the original value.
   * @param values the optional exact values that needs to be present in the toString of the
   *               original value.
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Integer> contains(final Integer value, final Integer... values) {
    return contains(value, (Object[]) values);
  }

  /**
   * Checks whether the given Integers are not present anywhere in the value.
   *
   * @param value  the exact value that may not be present in the toString of the original value.
   * @param values the optional exact values that may not be present in the toString of the original
   *               value.
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Integer> doesNotContain(final Integer value, final Integer... values) {
    return doesNotContain(value, (Object[]) values);
  }

}

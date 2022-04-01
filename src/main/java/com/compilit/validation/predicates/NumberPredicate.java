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
   * @deprecated Because of function ambiguity when importing static parent classes, it is advised to use the isAnIntegerEqualTo function.
   */
  @Deprecated
  public static Predicate<Integer> isEqualTo(final int value) {
    return isAnIntegerEqualTo(value);
  }

  /**
   * Check if the actual value is equal to the given one.
   *
   * @param value the exact expected value.
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Integer> isAnIntegerEqualTo(final int value) {
    return ObjectPredicate.isEqualTo(value);
  }

  /**
   * Check if the actual value is not equal to the given one.
   *
   * @param value the exact expected value.
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Integer> isAnIntegerNotEqualTo(final int value) {
    return isAnIntegerEqualTo(value).negate();
  }

  /**
   * @param amountOfDigits the exact amount of digits of the Integer.
   * @return Predicate to continue adding rules.
   * @deprecated Because of function ambiguity when importing static parent classes, it is advised to use the isAnIntegerWithAmountOfDigits function.
   */
  @Deprecated
  public static Predicate<Integer> hasAmountOfDigits(final int amountOfDigits) {
    return new NumberPredicate(x -> String.valueOf(x).length() == amountOfDigits);
  }

  /**
   * @param amountOfDigits the exact amount of digits of the Integer.
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Integer> isAnIntegerWithAmountOfDigits(final int amountOfDigits) {
    return new NumberPredicate(x -> String.valueOf(x).length() == amountOfDigits);
  }

  /**
   * @param first the first (inclusive) constraint. Can be either the high constraint or the low
   *              constraint.
   * @return a ChainingPredicate to add the second constraint.
   * @deprecated Because of function ambiguity when importing static parent classes, it is advised to use the isAnIntegerBetween function.
   */
  @Deprecated
  public static ConstraintAppender<Integer, Predicate<Integer>> isBetween(final int first) {
    return isAnIntegerBetween(first);
  }

  /**
   * @param first the first (inclusive) constraint. Can be either the high constraint or the low
   *              constraint.
   * @return a ChainingPredicate to add the second constraint.
   */
  public static ConstraintAppender<Integer, Predicate<Integer>> isAnIntegerBetween(final int first) {
    return new NumberConstraintAppender<>(first);
  }

  /**
   * Checks whether the given Integers are present anywhere in the value.
   *
   * @param value  the exact value that needs to be present in the toString of the original value.
   * @param values the optional exact values that needs to be present in the toString of the
   *               original value.
   * @return Predicate to continue adding rules.
   * @deprecated Because of function ambiguity when importing static parent classes, it is advised to use the isAnIntegerContaining function.
   * */
  @Deprecated
  public static Predicate<Integer> contains(final Integer value, final Integer... values) {
    return isAnIntegerContaining(value, values);
  }

  /**
   * Checks whether the given Integers are present anywhere in the value.
   *
   * @param value  the exact value that needs to be present in the toString of the original value.
   * @param values the optional exact values that needs to be present in the toString of the
   *               original value.
   * @return Predicate to continue adding rules.
   * */
  public static Predicate<Integer> isAnIntegerContaining(final Integer value, final Integer... values) {
    return contains(value, (Object[]) values);
  }

  /**
   * Checks whether the given Integers are not present anywhere in the value.
   *
   * @param value  the exact value that may not be present in the toString of the original value.
   * @param values the optional exact values that may not be present in the toString of the original
   *               value.
   * @return Predicate to continue adding rules.
   * @deprecated Because of function ambiguity when importing static parent classes, it is advised to use the isAnIntegerNotContaining function.
   * */
  @Deprecated
  public static Predicate<Integer> doesNotContain(final Integer value, final Integer... values) {
    return isAnIntegerNotContaining(value, values);
  }

  /**
   * Checks whether the given Integers are not present anywhere in the value.
   *
   * @param value  the exact value that may not be present in the toString of the original value.
   * @param values the optional exact values that may not be present in the toString of the original
   *               value.
   * @return Predicate to continue adding rules.
   * */
  public static Predicate<Integer> isAnIntegerNotContaining(final Integer value, final Integer... values) {
    return doesNotContain(value, (Object[]) values);
  }

}

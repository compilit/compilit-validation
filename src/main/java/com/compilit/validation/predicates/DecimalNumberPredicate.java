package com.compilit.validation.predicates;

import java.util.function.Predicate;

public final class DecimalNumberPredicate extends ObjectPredicate<Double> {

  private DecimalNumberPredicate(final Predicate<Double> predicate) {
    super(predicate);
  }

  /**
   * Checks whether the actual value is present.*
   *
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Double> isNotNull() {
    return ObjectPredicate.isNotNull();
  }

  /**
   * Checks whether the actual value is not present.
   *
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Double> isNull() {
    return ObjectPredicate.isNull();
  }

  /**
   * @param first the first (inclusive) constraint. Can be either the high constraint or the low
   *              constraint.
   * @return a ChainingPredicate to add the second constraint.
   * @deprecated Because of function ambiguity when importing static parent classes, it is advised to use the isADecimalNumberBetween function.
   */
  @Deprecated
  public static ConstraintAppender<Double, Predicate<Double>> isBetween(final double first) {
    return isADecimalNumberBetween(first);
  }

  /**
   * @param first the first (inclusive) constraint. Can be either the high constraint or the low
   *              constraint.
   * @return a ChainingPredicate to add the second constraint.
   */
  public static ConstraintAppender<Double, Predicate<Double>> isADecimalNumberBetween(final double first) {
    return new NumberConstraintAppender<>(first);
  }

  /**
   * Check if the actual value is equal to the given one.
   *
   * @param value the exact expected value.
   * @return Predicate to continue adding rules.
   * @deprecated Because of function ambiguity when importing static parent classes, it is advised to use the isADecimalNumberContaining function.
   */
  @Deprecated
  public static Predicate<Double> isAEqualTo(final double value) {
    return isADecimalNumberEqualTo(value);
  }

  /**
   * Check if the actual value is equal to the given one.
   *
   * @param value the exact expected value.
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Double> isADecimalNumberEqualTo(final double value) {
    return ObjectPredicate.isEqualTo(value);
  }

  /**
   * Checks whether the given Integers are present anywhere in the value.
   *
   * @param value  the exact value that needs to be present in the toString of the original value.
   * @param values the optional exact values that needs to be present in the toString of the
   *               original value.
   * @return Predicate to continue adding rules.
   * @deprecated Because of function ambiguity when importing static parent classes, it is advised to use the isADecimalNumberContaining function.
   * */
  @Deprecated
  public static Predicate<Double> contains(final Integer value, final Integer... values) {
    return isADecimalNumberContaining(value, values);
  }

  /**
   * Checks whether the given Integers are present anywhere in the value.
   *
   * @param value  the exact value that needs to be present in the toString of the original value.
   * @param values the optional exact values that needs to be present in the toString of the
   *               original value.
   * @return Predicate to continue adding rules.
   * */
  public static Predicate<Double> isADecimalNumberContaining(final Integer value, final Integer... values) {
    return contains(value, (Object[]) values);
  }

  /**
   * Checks whether the given Integers are not present anywhere in the value.
   *
   * @param value  the exact value that may not be present in the toString of the original value.
   * @param values the optional exact values that may not be present in the toString of the original
   *               value.
   * @return Predicate to continue adding rules.
   * @deprecated Because of function ambiguity when importing static parent classes, it is advised to use the isADecimalNumberNotContaining function.
   * */
  @Deprecated
  public static Predicate<Double> doesNotContain(final Integer value, final Integer... values) {
    return isADecimalNumberNotContaining(value, values);
  }

  /**
   * Checks whether the given Integers are not present anywhere in the value.
   *
   * @param value  the exact value that may not be present in the toString of the original value.
   * @param values the optional exact values that may not be present in the toString of the original
   *               value.
   * @return Predicate to continue adding rules.
   * */
  public static Predicate<Double> isADecimalNumberNotContaining(final Integer value, final Integer... values) {
    return doesNotContain(value, (Object[]) values);
  }

}

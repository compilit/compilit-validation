package org.solidcoding.validation.predicates;

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
   * @param first the first (inclusive) constraint. Can be either the high constraint or the low
   *              constraint.
   * @return a ChainingPredicate to add the second constraint.
   */
  public static ConstraintAppender<Double, Predicate<Double>> isBetween(final double first) {
    return new DecimalNumberConstraintAppender(first);
  }

  /**
   * Check if the actual value is equal to the given one.
   *
   * @param value the exact expected value.
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Double> isEqualTo(final double value) {
    return ObjectPredicate.isEqualTo(value);
  }

  /**
   * Checks whether the given Integers are present anywhere in the value.
   *
   * @param value  the exact value that needs to be present in the toString of the original value.
   * @param values the optional exact values that needs to be present in the toString of the
   *               original value.
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Double> contains(final Integer value, final Integer... values) {
    Predicate<Double> predicate = x -> x.toString().contains(value.toString());
    for (final var c : values) {
      predicate = predicate.and(x -> x.toString().contains(c.toString()));
    }
    return new DecimalNumberPredicate(predicate);
  }

  /**
   * Checks whether the given Integers are not present anywhere in the value.
   *
   * @param value  the exact value that may not be present in the toString of the original value.
   * @param values the optional exact values that may not be present in the toString of the original
   *               value.
   * @return Predicate to continue adding rules.
   */
  public static Predicate<Double> doesNotContain(final Integer value, final Integer... values) {
    Predicate<Double> predicate = x -> !x.toString().contains(value.toString());
    for (final var c : values) {
      predicate = predicate.and(x -> !x.toString().contains(c.toString()));
    }
    return new DecimalNumberPredicate(predicate);
  }


}

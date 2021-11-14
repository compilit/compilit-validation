package org.solidcoding.validation;

public class DecimalNumberConstraintPredicate {

  private final DecimalNumberPredicate originalPredicate;
  private final double first;

  DecimalNumberConstraintPredicate(double first, DecimalNumberPredicate originalPredicate) {
    this.first = first;
    this.originalPredicate = originalPredicate;
  }

  /**
   * @param second second of the constraints. Can be either the high constraint or the low
   *               constraint.
   * @return DecimalNumberPredicate to continue adding rules.
   */
  public DecimalNumberPredicate and(double second) {
    if (second > first) {
      originalPredicate.rules.add(x -> x <= second && x >= first);
    } else {
      originalPredicate.rules.add(x -> x <= first && x >= second);
    }
    return originalPredicate;
  }


}

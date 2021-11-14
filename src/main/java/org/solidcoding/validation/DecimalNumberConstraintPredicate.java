package org.solidcoding.validation;

class DecimalNumberConstraintPredicate implements ChainingPredicate<Double, DecimalNumberPredicate> {

  private final DecimalNumberPredicate originalPredicate;
  private final double first;

  DecimalNumberConstraintPredicate(Double first, DecimalNumberPredicate originalPredicate) {
    this.first = first;
    this.originalPredicate = originalPredicate;
  }

  /**
   * @param second second of the constraints. Can be either the high constraint or the low
   *               constraint.
   * @return DecimalNumberPredicate to continue adding rules.
   */
  public DecimalNumberPredicate and(Double second) {
    if (second > first) {
      originalPredicate.rules.add(x -> x <= second && x >= first);
    } else {
      originalPredicate.rules.add(x -> x <= first && x >= second);
    }
    return originalPredicate;
  }


}

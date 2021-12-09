package org.solidcoding.validation.predicates;

import java.util.function.Predicate;

final class DecimalNumberConstraintPredicateConnector
    implements PredicateConnector<Double, Predicate<Double>> {

  private final GenericPredicate<Double> originalPredicate;
  private final double first;

  DecimalNumberConstraintPredicateConnector(final Double first, GenericPredicate<Double> originalPredicate) {
    this.first = first;
    this.originalPredicate = originalPredicate;
  }

  /**
   * @param second the second of the (inclusive) constraints. Can be either the high constraint or
   *               the low constraint.
   * @return Predicate to continue adding rules.
   */
  @Override
  public Predicate<Double> and(final Double second) {
    if (second > first) {
      return originalPredicate.and(x -> x <= second && x >= first);
    } else {
      return originalPredicate.and(x -> x <= first && x >= second);
    }
  }

}

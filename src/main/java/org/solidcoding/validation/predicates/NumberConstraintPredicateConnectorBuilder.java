package org.solidcoding.validation.predicates;

import java.util.function.Predicate;

final class NumberConstraintPredicateConnectorBuilder
    implements PredicateConnector<Integer, Predicate<Integer>> {

  private final Predicate<Integer> originalPredicate;
  private final int first;

  NumberConstraintPredicateConnectorBuilder(final Integer first,
                                            final Predicate<Integer> originalPredicate) {
    this.first = first;
    this.originalPredicate = originalPredicate;
  }

  /**
   * @param second second of the constraints. Can be either the high constraint or the low
   *               constraint.
   * @return Predicate to continue adding rules.
   */
  @Override
  public Predicate<Integer> and(final Integer second) {
    if (second > first) {
      return originalPredicate.and(x -> x <= second && x >= first);
    } else {
      return originalPredicate.and(x -> x <= first && x >= second);
    }
  }

}

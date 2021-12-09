package org.solidcoding.validation.predicates;

import java.util.function.Predicate;

final class StringLengthConstraintPredicateConnectorBuilder
    implements PredicateConnector<Integer, Predicate<String>> {

  private final Predicate<String> originalPredicate;
  private final int first;

  StringLengthConstraintPredicateConnectorBuilder(final Integer first) {
    this.first = first;
    this.originalPredicate = new ObjectPredicateBuilder<>();
  }

  @Override
  public Predicate<String> and(final Integer second) {
    if (second > first) {
      return originalPredicate.and(x -> x.length() <= second && x.length() >= first);
    } else {
     return originalPredicate.and(x -> x.length() <= first && x.length() >= second);
    }
  }

}

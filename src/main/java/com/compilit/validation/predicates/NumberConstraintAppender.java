package com.compilit.validation.predicates;

import java.util.function.Predicate;

final class NumberConstraintAppender
        implements ConstraintAppender<Integer, Predicate<Integer>> {

  private final int first;

  NumberConstraintAppender(final int first) {
    this.first = first;
  }

  /**
   * @param second second of the constraints. Can be either the high constraint or the low
   *               constraint.
   * @return Predicate to continue adding rules.
   */
  @Override
  public Predicate<Integer> and(final Integer second) {
    if (second > first) {
      return x -> x <= second && x >= first;
    } else {
      return x -> x <= first && x >= second;
    }
  }

}

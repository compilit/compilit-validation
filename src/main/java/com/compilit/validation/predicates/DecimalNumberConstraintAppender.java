package com.compilit.validation.predicates;

import java.util.function.Predicate;

final class DecimalNumberConstraintAppender
        implements ConstraintAppender<Double, Predicate<Double>> {

  private final double first;

  DecimalNumberConstraintAppender(final double first) {
    this.first = first;
  }

  @Override
  public Predicate<Double> and(final Double second) {
    if (second > first) {
      return x -> x <= second && x >= first;
    } else {
      return x -> x <= first && x >= second;
    }
  }

}

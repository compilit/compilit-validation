package org.solidcoding.validation.api;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

final class RuleDefinitionBuilder<T> implements RuleBuilder<T> {

  private final Predicate<T> predicate;

  RuleDefinitionBuilder(final Predicate<T> predicate) {
    this.predicate = predicate;
  }

  @Override
  public Rule<T> otherWiseReport(final String failMessage, final Object... formatArguments) {
    if (formatArguments != null) {
      final var actualMessage = String.format(failMessage, formatArguments);
      return new RuleDefinition<>(predicate, actualMessage);
    }
    return new RuleDefinition<>(predicate, failMessage);
  }

  static final class Extended<T> implements RuleBuilder.Extended<T> {

    private final BiPredicate<T, Object> predicate;

    public Extended(final BiPredicate<T, Object> predicate) {
      this.predicate = predicate;
    }

    @Override
    public Rule.Extended<T> otherWiseReport(final String failMessage, final Object... formatArguments) {
      if (formatArguments != null) {
        final var actualMessage = String.format(failMessage, formatArguments);
        return new RuleDefinition.Extended<>(predicate, actualMessage);
      }
      return new RuleDefinition.Extended<>(predicate, failMessage);
    }

  }
}

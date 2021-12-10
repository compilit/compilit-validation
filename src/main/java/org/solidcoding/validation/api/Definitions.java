package org.solidcoding.validation.api;

import org.solidcoding.validation.api.contracts.RuleBuilder;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class Definitions {

  private Definitions() {
  }

  public static <T> RuleBuilder<T> defineThatIt(Predicate<T> predicate) {
    return new RuleDefinitionBuilder<>(predicate);
  }

  public static <T> RuleBuilder.Extended<T> defineThatIt(BiPredicate<T, Object> predicate) {
    return new RuleDefinitionBuilder.Extended<>(predicate);
  }

}

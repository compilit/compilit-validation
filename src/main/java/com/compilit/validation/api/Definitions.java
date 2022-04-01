package com.compilit.validation.api;

import com.compilit.validation.api.contracts.RuleBuilder;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class Definitions {

  private Definitions() {
  }

  public static <T> RuleBuilder<T> defineThatIt(Predicate<T> predicate) {
    return new RuleDefinitionBuilder<>(predicate);
  }

  public static <T> RuleBuilder.WithDualInput<T> defineThatIt(BiPredicate<T, Object> predicate) {
    return new RuleDefinitionBuilder.WithDualInput<>(predicate);
  }

}

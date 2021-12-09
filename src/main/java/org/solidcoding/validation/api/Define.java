package org.solidcoding.validation.api;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class Define {

  private Define() {
  }

  public static <T> RuleBuilder<T> thatIt(Predicate<T> predicate) {
    return new RuleDefinitionBuilder<>(predicate);
  }

  public static <T> RuleBuilder.Extended<T> thatIt(BiPredicate<T, Object> predicate) {
    return new RuleDefinitionBuilder.Extended<>(predicate);
  }

}

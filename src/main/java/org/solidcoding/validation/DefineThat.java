package org.solidcoding.validation;

import java.util.function.Predicate;

public class DefineThat {

  private DefineThat() {}

  public static <T> Rule<T> itShould(Predicate<T> rule) {
    return new BusinessRule<>(rule);
  }

}

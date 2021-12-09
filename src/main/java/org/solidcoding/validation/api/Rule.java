package org.solidcoding.validation.api;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface Rule<T> extends Predicate<T> {

  String getFailMessage();

  interface Extended<T> extends BiPredicate<T, Object> {
    String getFailMessage();
  }

}

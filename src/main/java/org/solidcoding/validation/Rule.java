package org.solidcoding.validation;

import java.util.function.Predicate;

public interface Rule<T> {
  String getMessage();
  Predicate<T> getPredicate();
}

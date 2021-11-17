package org.solidcoding.validation;

import java.util.function.Predicate;

public interface Rule<T> extends Predicate<T> {
  String getFailMessage();
}

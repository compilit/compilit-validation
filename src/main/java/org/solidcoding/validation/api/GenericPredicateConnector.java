package org.solidcoding.validation.api;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface GenericPredicateConnector<T> {
  /**
   * @param predicate the custom predicate to test against properties of T.
   * @return Predicate to continue adding rules.
   */
  Predicate<T> where(final Predicate<T> predicate);

  /**
   * @param predicate the custom predicate to test against properties of T.
   * @return Predicate to continue adding rules.
   */
  BiPredicate<T, Object> where(final BiPredicate<T, Object> predicate);
}

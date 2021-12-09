package org.solidcoding.validation.predicates;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.solidcoding.validation.api.GenericPredicateConnector;

public interface GenericPredicateRule<T> extends GenericPredicateConnector<T> {

  boolean isPredicate();

  boolean isBiPredicate();

  default Predicate<T> getPredicate() {
    return x -> false;
  }

  default BiPredicate<T, Object> getBiPredicate() {
    return (x, y) -> false;
  }

  default boolean test(final T value) {
    return getPredicate().test(value);
  }

  default boolean test(final T value, final Object argument) {
    return getBiPredicate().test(value, argument);
  }

  void addPredicate(BiPredicate<T, Object> biPredicate);

  void addPredicate(Predicate<T> predicate);

  /**
   * @param second second of the constraints. Can be either the high constraint or the low
   *               constraint.
   * @return a Predicate to continue adding rules.
   */
  GenericPredicateRule<T> and(GenericPredicateRule<T> second);

  /**
   * @param predicate the custom predicate to test against properties of T.
   * @return Predicate to continue adding rules.
   */
  @Override
  default Predicate<T> where(final Predicate<T> predicate) {
    return predicate.and(this::test);
  }

  /**
   * @param predicate the custom predicate to test against properties of T.
   * @return Predicate to continue adding rules.
   */
  @Override
  default BiPredicate<T, Object> where(final BiPredicate<T, Object> predicate) {
    return predicate.and(this::test);
  }
}

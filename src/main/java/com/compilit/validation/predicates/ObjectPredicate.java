package com.compilit.validation.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ObjectPredicate<T> implements Predicate<T>, PredicateAppender<T> {

  private final List<Predicate<T>> predicates = new ArrayList<>();

  private ObjectPredicate() {
  }

  protected ObjectPredicate(Predicate<T> predicate) {
    predicates.add(predicate);
  }

  /**
   * @param clazz the class of the object to validate. This is only a compiler flag to treat T as
   *              its instance.
   * @param <T>   the type upon which the validations are tested.
   * @return GenericPredicateRule to continue adding rules.
   */
  @SuppressWarnings("unused")
  public static <T> PredicateAppender<T> isA(final Class<T> clazz) {
    return new ObjectPredicate<>();
  }

  /**
   * @param clazz the class of the object to validate. This is only a compiler flag to treat T as
   *              its instance.
   * @param <T>   the type upon which the validations are tested.
   * @return GenericPredicateRule to continue adding rules.
   */
  public static <T> PredicateAppender<T> isAn(final Class<T> clazz) {
    return isA(clazz);
  }

  /**
   * Check if the actual value is equal to the given one.
   *
   * @param value the exact expected value.
   * @param <T>   the value which you wish to test against the original value.
   * @return Predicate to continue adding rules.
   */
  public static <T> Predicate<T> isEqualTo(T value) {
    return new ObjectPredicate<>(x -> Objects.equals(x, value));
  }

  static <T> Predicate<T> isNotNull() {
    return new ObjectPredicate<>(Objects::nonNull);
  }

  static <T> Predicate<T> isNull() {
    return new ObjectPredicate<>(Objects::isNull);
  }

  static <T> Predicate<T> contains(Object value, Object... values) {
    Predicate<T> predicate = x -> x.toString().contains(value.toString());
    for (final var c : values) {
      predicate = predicate.and(x -> x.toString().contains(c.toString()));
    }
    return new ObjectPredicate<>(predicate);
  }

  static <T> Predicate<T> doesNotContain(Object value, Object... values) {
    Predicate<T> predicate = x -> !x.toString().contains(value.toString());
    for (final var c : values) {
      predicate = predicate.and(x -> !x.toString().contains(c.toString()));
    }
    return new ObjectPredicate<>(predicate);
  }

  @Override
  public boolean test(T value) {
    return predicates.stream().allMatch(x -> x.test(value));
  }

}

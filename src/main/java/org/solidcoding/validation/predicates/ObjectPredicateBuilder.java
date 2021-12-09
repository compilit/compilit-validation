package org.solidcoding.validation.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ObjectPredicateBuilder<T> implements GenericPredicate<T> {

    private final List<Predicate<T>> predicates = new ArrayList<>();

    protected ObjectPredicateBuilder() {
    }

    protected ObjectPredicateBuilder(Predicate<T> predicate) {
        predicates.add(predicate);
    }

    protected ObjectPredicateBuilder(List<Predicate<T>> predicates) {
        this.predicates.addAll(predicates);
    }

    /**
     * Checks whether the actual value is present.
     * @param <T> the type upon which the validations are tested.
     * @return Predicate to continue adding rules.
     */
    public static <T> Predicate<T> isNotNull() {
        return new ObjectPredicateBuilder<>(Objects::nonNull);
    }

    /**
     * @param clazz the class of the object to validate. This is only a compiler flag to treat T as its instance.
     * @param <T>   the type upon which the validations are tested.
     * @return GenericPredicate to continue adding rules.
     */
    @SuppressWarnings("unused")
    public static <T> GenericPredicate<T> isA(Class<T> clazz) {
        return new ObjectPredicateBuilder<>();
    }

    /**
     * @param clazz the class of the object to validate. This is only a compiler flag to treat T as its instance.
     * @param <T>   the type upon which the validations are tested.
     * @return GenericPredicate to continue adding rules.
     */
    public static <T> GenericPredicate<T> isAn(Class<T> clazz) {
        return isA(clazz);
    }

    /**
     * Check if the actual value is equal to the given one.
     *
     * @param value the exact expected value.
     * @return Predicate to continue adding rules.
     */
    public static <T> Predicate<T> isEqualTo(T value) {
        return new ObjectPredicateBuilder<>(x -> Objects.equals(x, value));
    }

    @Override
    public boolean test(T value) {
        return predicates.stream().allMatch(x -> x.test(value));
    }

    @Override
    public Predicate<T> where(Predicate<T> predicate) {
        addPredicate(predicate);
        return this;
    }

    static <T> GenericPredicate<T> contains(Object value, Object... values) {
        var rules = new ArrayList<Predicate<T>>();
        rules.add(x -> x.toString().contains(value.toString()));
        for (var v : values) {
            rules.add(x -> x.toString().contains(v.toString()));
        }
        return new ObjectPredicateBuilder<>(rules);
    }

    static <T> GenericPredicate<T> doesNotContain(Object value, Object... values) {
        var rules = new ArrayList<Predicate<T>>();
        rules.add(x -> !x.toString().contains(value.toString()));
        for (var v : values) {
            rules.add(x -> !x.toString().contains(v.toString()));
        }
        return new ObjectPredicateBuilder<>(rules);
    }

    void addPredicate(Predicate<T> predicate) {
        predicates.add(predicate);
    }

//    public static class Extended<T> implements GenericPredicateRule<T> {
//
//      private final List<Predicate<T>> predicates = new ArrayList<>();
//      private final List<BiPredicate<T, Object>> biPredicates = new ArrayList<>();
//
//      private Extended() {
//      }
//
////  protected ObjectPredicateRuleBuilder(final Predicate<T> predicate) {
////    predicates.add(predicate);
////  }
////
////  protected ObjectPredicateRuleBuilder(final List<Predicate<T>> predicates) {
////    this.predicates.addAll(predicates);
////  }
//
////  /**
////   * Checks whether the actual value is present.
////   *
////   * @param <T> the type upon which the validations are tested.
////   * @return Predicate to continue adding rules.
////   */
////  public static <T> GenericPredicateRule<T> isNotNull() {
////    return new ObjectPredicateRuleBuilder<>(Objects::nonNull);
////  }
//
//      /**
//       * @param clazz the class of the object to validate. This is only a compiler flag to treat T as
//       *              its instance.
//       * @param <T>   the type upon which the validations are tested.
//       * @return GenericPredicateRule to continue adding rules.
//       */
//      @SuppressWarnings("unused")
//      public static <T> GenericPredicateRule<T> isA(final Class<T> clazz) {
//        return new Extended<>();
//      }
//
//      /**
//       * @param clazz the class of the object to validate. This is only a compiler flag to treat T as
//       *              its instance.
//       * @param <T>   the type upon which the validations are tested.
//       * @return GenericPredicateRule to continue adding rules.
//       */
//      public static <T> GenericPredicateRule<T> isAn(final Class<T> clazz) {
//        return isA(clazz);
//      }
//
////  /**
////   * Check if the actual value is equal to the given one.
////   *
////   * @param value the exact expected value.
////   * @return Predicate to continue adding rules.
////   */
////  public static <T> GenericPredicateRule<T> isEqualTo(final T value) {
////    return new ObjectPredicateRuleBuilder<>(x -> Objects.equals(x, value));
////  }
//
////  static <T> GenericPredicateRule<T> contains(final Object value, final Object... values) {
////    final var rules = new ArrayList<Predicate<T>>();
////    rules.add(x -> x.toString().contains(value.toString()));
////    for (final var v : values) {
////      rules.add(x -> x.toString().contains(v.toString()));
////    }
////    return new ObjectPredicateRuleBuilder<>(rules);
////  }
////
////  static <T> GenericPredicateRule<T> doesNotContain(final Object value, final Object... values) {
////    final var rules = new ArrayList<Predicate<T>>();
////    rules.add(x -> !x.toString().contains(value.toString()));
////    for (final var v : values) {
////      rules.add(x -> !x.toString().contains(v.toString()));
////    }
////    return new ObjectPredicateRuleBuilder<>(rules);
////  }
//
//      @Override
//      public boolean isPredicate() {
//        return biPredicates.isEmpty() && !predicates.isEmpty();
//      }
//
//      @Override
//      public boolean isBiPredicate() {
//        return !biPredicates.isEmpty() && predicates.isEmpty();
//      }
//
//      @Override
//      public Predicate<T> where(final Predicate<T> predicate) {
//        addPredicate(predicate);
//        return getPredicate();
//      }
//
//      void addPredicate(final Predicate<T> predicate) {
//        predicates.add(predicate);
//      }
//
//      @Override
//      public Predicate<T> getPredicate() {
//        return value -> predicates.stream().allMatch(predicate -> predicate.test(value));
//      }
//
//      @Override
//      public BiPredicate<T, Object> getBiPredicate() {
//        return (value, argument) -> biPredicates.stream().allMatch(biPredicate -> biPredicate.test(value, argument));
//      }
//    }
}

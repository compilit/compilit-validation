package org.solidcoding.validation.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.solidcoding.validation.api.GenericPredicateConnector;

public class ObjectPredicateRuleBuilder<T> implements GenericPredicateRule<T> {

  private final List<Predicate<T>> predicates = new ArrayList<>();
  private final List<BiPredicate<T, Object>> biPredicates = new ArrayList<>();

  ObjectPredicateRuleBuilder() {
  }

  protected ObjectPredicateRuleBuilder(final Predicate<T> predicate) {
    predicates.add(predicate);
  }

  protected ObjectPredicateRuleBuilder(final List<Predicate<T>> predicates) {
    this.predicates.addAll(predicates);
  }

  /**
   * @param clazz the class of the object to validate. This is only a compiler flag to treat T as
   *              its instance.
   * @param <T>   the type upon which the validations are tested.
   * @return GenericPredicateRule to continue adding rules.
   */
  @SuppressWarnings("unused")
  public static <T> GenericPredicateConnector<T> isA(final Class<T> clazz) {
    return new ObjectPredicateRuleBuilder<>();
  }

  /**
   * @param clazz the class of the object to validate. This is only a compiler flag to treat T as
   *              its instance.
   * @param <T>   the type upon which the validations are tested.
   * @return GenericPredicateRule to continue adding rules.
   */
  public static <T> GenericPredicateConnector<T> isAn(final Class<T> clazz) {
    return isA(clazz);
  }

  /**
   * Check if the actual value is equal to the given one.
   *
   * @param value the exact expected value.
   * @return Predicate to continue adding rules.
   */
  public static <T> GenericPredicateRule<T> isEqualTo(final T value) {
    return new ObjectPredicateRuleBuilder<>(x -> Objects.equals(x, value));
  }

  @Override
  public boolean isPredicate() {
    return biPredicates.isEmpty() && !predicates.isEmpty();
  }

  @Override
  public boolean isBiPredicate() {
    return !biPredicates.isEmpty() && predicates.isEmpty();
  }

//  @Override
//  public Predicate<T> where(final Predicate<T> predicate) {
//    addPredicate(predicate);
//    return getPredicate();
//  }

  @Override
  public void addPredicate(final Predicate<T> predicate) {
    predicates.add(predicate);
  }

  @Override
  public void addPredicate(final BiPredicate<T, Object> biPredicate) {
    biPredicates.add(biPredicate);
  }

  @Override
  public Predicate<T> getPredicate() {
    return value -> predicates.stream().allMatch(y -> y.test(value));
  }

  @Override
  public BiPredicate<T, Object> getBiPredicate() {
    return (value, argument) -> biPredicates.stream().allMatch((x) -> x.test(value, argument));
  }

  @Override
  public GenericPredicateRule<T> and(GenericPredicateRule<T> second) {
    if(second.isBiPredicate()) {
      addPredicate(second.getBiPredicate());
    }
    if(second.isPredicate()) {
      addPredicate(getPredicate());
    }
    return this;
  }
}

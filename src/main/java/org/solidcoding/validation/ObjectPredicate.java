package org.solidcoding.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ObjectPredicate<T> implements Predicate<T> {

  public List<Predicate<T>> rules = new ArrayList<>();

  private ObjectPredicate() {}

  private ObjectPredicate(Predicate<T> rule) {
    this.rules.add(rule);
  }

  /**
   * @param clazz the class of the object to validate. This is only a compiler flag.
   * @param <T> the type upon which the validations are tested.
   * @return ObjectPredicate to continue adding rules.
   */
  public static <T> ObjectPredicate<T> beA(Class<T> clazz) {
    return new ObjectPredicate<>();
  }

  /**
   * @param rule the custom predicate to test against the T.
   * @return TPredicate to continue adding rules.
   */
  public ObjectPredicate<T> that(Predicate<T> rule) {
    return new ObjectPredicate<>(rule);
  }

  @Override
  public boolean test(T value) {
    return rules.stream().allMatch(x -> x.test(value));
  }

}

package org.solidcoding.validation.api.contracts;

public interface ArgumentAppender<T> {

  /**
   * You've entered a BiPredicate flow. This argument will be the second argument next to the regular one to use in your predicate.
   * When you wish to compare your value with another one for example, the BiPredicate would look like: (x, y) -> x.compareTo(y);
   *
   * @param argument the second argument in your BiPredicate.
   * @return ContinuingValidationBuilder to continue adding rules.
   */
  ContinuingValidationBuilder<T> whileApplying(Object argument);

}

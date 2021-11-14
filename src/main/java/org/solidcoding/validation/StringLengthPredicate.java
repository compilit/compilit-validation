package org.solidcoding.validation;

public final class StringLengthPredicate {

  private final StringPredicate originalPredicate;
  private final int first;

  StringLengthPredicate(int first, StringPredicate originalPredicate) {
    this.first = first;
    this.originalPredicate = originalPredicate;
  }

  /**
   * @param second second of the constraints. Can be either the high constraint or the low
   *               constraint.
   * @return StringPredicate to continue adding rules.
   */
  public StringPredicate and(int second) {
    if (second > first) {
      originalPredicate.rules.add(x -> x.length() <= second && x.length() >= first);
    } else {
      originalPredicate.rules.add(x -> x.length() <= first && x.length() >= second);
    }
    return originalPredicate;
  }

}

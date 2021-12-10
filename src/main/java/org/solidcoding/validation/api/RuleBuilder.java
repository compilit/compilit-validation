package org.solidcoding.validation.api;

public interface RuleBuilder<T> {

  /**
   * @param message         the message you wish to add to the pipe in case of a failed validation.
   * @param formatArguments the extra arguments you wish to place on the standardized format '%s' placeholders.
   * @return the finished Rule, ready to validate.
   */
  Rule<T> otherwiseReport(String message, Object... formatArguments);

  interface Extended<T> {

    /**
     * @param message         the message you wish to add to the pipe in case of a failed validation.
     * @param formatArguments the extra arguments you wish to place on the standardized format '%s' placeholders.
     * @return the finished Rule, ready to validate.
     */
    Rule.Extended<T> otherwiseReport(String message, Object... formatArguments);
  }
}

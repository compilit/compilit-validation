package org.solidcoding.validation.api.contracts;

public interface Validator {

  /**
   * @return boolean true if all rules pass. False if at least one rule fails.
   */
  boolean validate();

  /**
   * @return the message containing information about the validation. Default to 'Nothing to report'.
   */
  String getMessage();

}

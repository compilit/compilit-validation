package org.solidcoding.validation.api;

import org.slf4j.Logger;

public interface LoggingValidator {

  /**
   * Users SLF4J API to log the internal message at Error level. Using the Validations class as its logger source.
   *
   */
  void orElseLogMessage();

  /**
   * Users SLF4J API to log the internal message at Error level. Using the Validations class as its logger source.
   *
   * @param logger the logger used to log the message.
   */
  void orElseLogMessage(Logger logger);

}

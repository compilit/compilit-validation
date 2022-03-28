package com.compilit.validation.api.contracts;

import org.slf4j.Logger;

public interface LoggingValidator {

  /**
   * Users SLF4J API to log the internal message at Error level. Using the Validations class as its logger source.
   *
   * @return T the wanted return type.
   */
  boolean orElseLogMessage();

  /**
   * Users SLF4J API to log the internal message at Error level. Using the Validations class as its logger source.
   *
   * @param logger the logger used to log the message.
   * @return T the wanted return type.
   */
  boolean orElseLogMessage(Logger logger);

}

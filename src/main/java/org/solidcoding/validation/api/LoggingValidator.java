package org.solidcoding.validation.api;

import org.slf4j.Logger;

public interface LoggingValidator {

  /**
   * Users SLF4J API to log the internal message at Info level. Using the Validations class as its logger source.
   *
   */
  void orElseLogInfo();

  /**
   * Users SLF4J API to log the internal message at Warning level. Using the Validations class as its logger source.
   *
   */
  void orElseLogWarn();

  /**
   * Users SLF4J API to log the internal message at Error level. Using the Validations class as its logger source.
   *
   */
  void orElseLogError();

  /**
   * Users SLF4J API to log the internal message at Info level.
   *
   * @param logger the logger used to log the message.
   */
  void orElseLogInfo(Logger logger);

  /**
   * Users SLF4J API to log the internal message at Warning level.
   *
   * @param logger the logger used to log the message.
   */
  void orElseLogWarn(Logger logger);

  /**
   * Users SLF4J API to log the internal message at Error level.
   *
   * @param logger the logger used to log the message.
   */
  void orElseLogError(Logger logger);

}

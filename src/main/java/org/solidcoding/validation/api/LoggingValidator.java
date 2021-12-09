package org.solidcoding.validation.api;

import org.slf4j.Logger;

interface LoggingValidator {

  /**
   * Users SLF4J API to log the internal failMessage at Info level.
   *
   * @param logger the logger used to log the message.
   */
  void orElseLogInfo(Logger logger);

  /**
   * Users SLF4J API to log the internal failMessage at Warning level.
   *
   * @param logger the logger used to log the message.
   */
  void orElseLogWarn(Logger logger);

  /**
   * Users SLF4J API to log the internal failMessage at Error level.
   *
   * @param logger the logger used to log the message.
   */
  void orElseLogError(Logger logger);

}

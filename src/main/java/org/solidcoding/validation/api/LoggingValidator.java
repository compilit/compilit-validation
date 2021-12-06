package org.solidcoding.validation.api;

import org.slf4j.Logger;

interface LoggingValidator {
    /**
     * Users SLF4J API to log the internal failMessage at Info level.
     */
    void orElseLogInfo(Logger logger);

    /**
     * Users SLF4J API to log the internal failMessage at Warning level.
     */
    void orElseLogWarn(Logger logger);

    /**
     * Users SLF4J API to log the internal failMessage at Error level.
     */
    void orElseLogError(Logger logger);

}

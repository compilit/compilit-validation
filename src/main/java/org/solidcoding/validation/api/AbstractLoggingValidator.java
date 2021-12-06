package org.solidcoding.validation.api;

import org.slf4j.Logger;

public abstract class AbstractLoggingValidator<T> implements LoggingValidator {

    protected final ContinuingValidator<T> validator;

    protected AbstractLoggingValidator(ContinuingValidator<T> validator) {
        this.validator = validator;
    }

    @Override
    public void orElseLogInfo(Logger logger) {
        var isValid = validator.validate();
        if (!isValid) {
            logger.info(validator.getMessage());
        }
    }

    @Override
    public void orElseLogWarn(Logger logger) {
        var isValid = validator.validate();
        if (!isValid) {
            logger.warn(validator.getMessage());
        }
    }

    @Override
    public void orElseLogError(Logger logger) {
        var isValid = validator.validate();
        if (!isValid) {
            logger.error(validator.getMessage());
        }
    }

}

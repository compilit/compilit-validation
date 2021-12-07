package org.solidcoding.validation.api;

import java.util.function.Function;

final class VoidEndingValidator<T> extends AbstractLoggingValidator<T> implements VoidValidator {

    private final Runnable runnable;

    VoidEndingValidator(Runnable runnable, ContinuingValidator<T> validator) {
        super(validator);
        this.runnable = runnable;
    }

    @Override
    public <E extends RuntimeException> Void orElseThrow(Function<String, E> throwableFunction) {
        var isValid = validator.validate();
        if (!isValid) {
            throw throwableFunction.apply(validator.getMessage());
        }
        runnable.run();
        return null;
    }

}

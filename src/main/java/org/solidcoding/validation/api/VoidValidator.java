package org.solidcoding.validation.api;

import java.util.function.Function;

public interface VoidValidator<T> extends LoggingValidator, ThrowingValidator<Void> {

    /**
     * @param throwableFunction the function defining the Exception that needs to be thrown when a rule is broken. The String is the stored failure message of the validation.
     * @param <E>               the bound of the Exception that needs to be thrown when a rule is broken.
     */
    <E extends RuntimeException> Void orElseThrow(Function<String, E> throwableFunction);

}
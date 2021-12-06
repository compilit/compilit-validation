package org.solidcoding.validation.api;

import java.util.function.Function;
import java.util.function.Supplier;

final class EndingValidator<T, R> extends AbstractLoggingValidator<T> implements ReturningValidator<R> {

    private final Supplier<R> supplier;

    EndingValidator(Supplier<R> supplier, ContinuingValidator<T> validator) {
        super(validator);
        this.supplier = supplier;
    }

    public <E extends RuntimeException> R orElseThrow(Function<String, E> throwableFunction) {
        var isValid = validator.validate();
        if (!isValid) {
            throw throwableFunction.apply(validator.getMessage());
        }
        return supplier.get();
    }

    @Override
    public R orElseReturn(R other) {
        var isValid = validator.validate();
        if (!isValid) {
            return other;
        }
        return supplier.get();
    }

    @Override
    public R orElseReturn(Function<String, R> other) {
        var isValid = validator.validate();
        var message = validator.getMessage();
        if (!isValid) {
            return other.apply(message);
        }
        return supplier.get();
    }

}

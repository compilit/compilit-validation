package org.solidcoding.validation.api;

import java.util.function.Function;
import java.util.function.Supplier;

final class EndingRuleValidationBuilder<T, R> extends AbstractLoggingValidator<T>
        implements ReturningValidationBuilder<R> {

    private final Supplier<R> supplier;

    EndingRuleValidationBuilder(final Supplier<R> supplier, final ContinuingValidationBuilder<T> validator) {
        super(validator);
        this.supplier = supplier;
    }

    @Override
    public <E extends RuntimeException> R orElseThrow(final Function<String, E> throwableFunction) {
        final var isValid = validator.validate();
        if (!isValid) {
            throw throwableFunction.apply(validator.getMessage());
        }
        return supplier.get();
    }

    @Override
    public R orElseReturn(final R other) {
        final var isValid = validator.validate();
        if (!isValid) {
            return other;
        }
        return supplier.get();
    }

    @Override
    public R orElseReturn(final Function<String, R> other) {
        final var isValid = validator.validate();
        final var message = validator.getMessage();
        if (!isValid) {
            return other.apply(message);
        }
        return supplier.get();
    }

}

package org.solidcoding.validation.newapi;

import org.solidcoding.validation.api.AbstractLoggingValidator;
import org.solidcoding.validation.api.ContinuingValidationBuilder;
import org.solidcoding.validation.api.ReturningValidationBuilder;

import java.util.function.Function;
import java.util.function.Supplier;

final class EndingRuleValidationBuilder<T, R> extends AbstractLoggingValidator<T> implements ReturningValidationBuilder<R> {

    private final Supplier<R> supplier;

    EndingRuleValidationBuilder(Supplier<R> supplier, ContinuingValidationBuilder<T> validator) {
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

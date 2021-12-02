package org.solidcoding.validation.api;

import java.util.function.Function;
import java.util.function.Supplier;

final class EndingValidator<T, R> implements ReturningValidator<R> {

    private final Supplier<R> supplier;
    private final ContinuingValidator<T> businessRuleValidator;

    EndingValidator(Supplier<R> supplier, ContinuingValidator<T> businessRuleValidator) {
        this.supplier = supplier;
        this.businessRuleValidator = businessRuleValidator;
    }

    public <E extends RuntimeException> R orElseThrow(E throwable) {
        var isValid = businessRuleValidator.validate();
        if (!isValid) {
            throw throwable;
        }
        return supplier.get();
    }

    @Override
    public R orElseReturn(R other) {
        var isValid = businessRuleValidator.validate();
        if (!isValid) {
            return other;
        }
        return supplier.get();
    }

    @Override
    public R orElseReturn(Function<String, R> other) {
        var isValid = businessRuleValidator.validate();
        var message = businessRuleValidator.getMessage();
        if (!isValid) {
            return other.apply(message);
        }
        return supplier.get();
    }
}

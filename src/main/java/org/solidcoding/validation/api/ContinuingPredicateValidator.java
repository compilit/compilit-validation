package org.solidcoding.validation.api;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

final class ContinuingPredicateValidator<T> implements ContinuingValidator<T> {

    private final List<Rule<T>> ruleDefinitions;
    private final T value;
    private static final String DEFAULT_MESSAGE = "Nothing to report";
    private String message;

    ContinuingPredicateValidator(List<Rule<T>> ruleDefinitions, T value) {
        this.ruleDefinitions = ruleDefinitions;
        this.value = value;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ContinuingValidator<T> and(Rule<T> rule) {
        ruleDefinitions.add(rule);
        return this;
    }

    @Override
    public boolean validate() {
        message = DEFAULT_MESSAGE;
        var stringBuilder = new StringBuilder();
        var isValid = true;
        for (var ruleDefinition : ruleDefinitions) {
            if (!ruleDefinition.test(value)) {
                stringBuilder.append("Rule broken: ").append(ruleDefinition.getFailMessage()).append("\n");
                isValid = false;
            }
        }
        if (!isValid) {
            message = stringBuilder.toString();
        }
        return isValid;
    }

    @Override
    public <R> ReturningValidator<R> andThen(Supplier<R> supplier) {
        return new EndingValidator<T, R>(supplier, this);
    }


    @Override
    public <E extends RuntimeException> boolean orElseThrow(Function<String, E> throwableFunction) {
        var isValid = validate();
        if (!isValid) {
            throw throwableFunction.apply(getMessage());
        }
        return true;
    }

    @Override
    public T orElseReturn(T other) {
        var isValid = validate();
        if (!isValid) {
            return other;
        }
        return value;
    }

    @Override
    public T orElseReturn(Function<String, T> other) {
        var isValid = validate();
        var message = this.message;
        if (!isValid) {
            return other.apply(message);
        }
        return value;
    }


}

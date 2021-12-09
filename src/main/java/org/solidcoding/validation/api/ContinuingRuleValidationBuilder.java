package org.solidcoding.validation.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

final class ContinuingRuleValidationBuilder<T> implements ContinuingValidationBuilder<T> {

    static final String DEFAULT_MESSAGE = "Nothing to report";
    private final List<Rule<T>> ruleDefinitions = new ArrayList<>();
    private final List<Rule.Extended<T>> xRuleDefinitions = new ArrayList<>();
    private final T value;
    private final Object argument;
    private String message;
    private final boolean isExtended;

    ContinuingRuleValidationBuilder(List<Rule<T>> ruleDefinitions, T value) {
        this.ruleDefinitions.addAll(ruleDefinitions);
        this.value = value;
        this.argument = null;
        this.isExtended = false;
    }

    ContinuingRuleValidationBuilder(List<Rule.Extended<T>> xRuleDefinitions, T value, Object argument) {
        this.xRuleDefinitions.addAll(xRuleDefinitions);
        this.value = value;
        this.argument = argument;
        this.isExtended = true;
    }

    @Override
    public String getMessage() {
        if (message == null)
            return DEFAULT_MESSAGE;
        return message;
    }

    @Override
    public ContinuingValidationBuilder<T> and(Rule<T> rule) {
        ruleDefinitions.add(rule);
        return this;
    }

    @Override
    public boolean validate() {
        message = DEFAULT_MESSAGE;
        var stringBuilder = new StringBuilder();
        var isValid = true;
        if (isExtended) {
            for (var biRuleDefinition : xRuleDefinitions) {
                if (!biRuleDefinition.test(value, argument)) {
                    stringBuilder.append("Broken rule: ").append(biRuleDefinition.getFailMessage()).append("\n");
                    isValid = false;
                }
            }
        }
        for (var ruleDefinition : ruleDefinitions) {
            if (!ruleDefinition.test(value)) {
                stringBuilder.append("Broken rule: ").append(ruleDefinition.getFailMessage()).append("\n");
                isValid = false;
            }
        }
        if (!isValid) {
            message = stringBuilder.toString();
        }
        return isValid;
    }

    @Override
    public <R> ReturningValidationBuilder<R> andThen(Supplier<R> supplier) {
        return new EndingRuleValidationBuilder<>(supplier, this);
    }

    @Override
    public VoidEndingValidationBuilder andThen(Runnable runnable) {
        return new VoidEndingRuleValidatorBuilder<>(runnable, this);
    }


    @Override
    public <E extends RuntimeException> Boolean orElseThrow(Function<String, E> throwableFunction) {
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
        if (!isValid) {
            return other.apply(message);
        }
        return value;
    }

}

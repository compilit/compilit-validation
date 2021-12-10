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
    private final boolean isExtended;
    private String message = DEFAULT_MESSAGE;

    ContinuingRuleValidationBuilder(final List<Rule<T>> ruleDefinitions, final T value) {
        this.ruleDefinitions.addAll(ruleDefinitions);
        this.value = value;
        this.argument = null;
        this.isExtended = false;
    }

    ContinuingRuleValidationBuilder(final List<Rule.Extended<T>> xRuleDefinitions,
                                    final T value,
                                    final Object argument) {
        this.xRuleDefinitions.addAll(xRuleDefinitions);
        this.value = value;
        this.argument = argument;
        this.isExtended = true;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ContinuingValidationBuilder<T> and(final Rule<T> rule) {
        ruleDefinitions.add(rule);
        return this;
    }

    @Override
    public ContinuingValidationBuilder<T> and(Rule.Extended<T> rule) {
        xRuleDefinitions.add(rule);
        return this;
    }

    @Override
    public boolean validate() {
        final var stringBuilder = new StringBuilder();
        var isValid = true;
        if (isExtended) {
            isValid = validateExtendedRules(stringBuilder);
        } else {
            isValid = validateRules(stringBuilder);
        }
        if (!isValid) {
            message = stringBuilder.toString();
        }
        return isValid;
    }

    private boolean validateRules(StringBuilder stringBuilder) {
        var isValid = true;
        for (final var ruleDefinition : ruleDefinitions) {
            if (!ruleDefinition.test(value)) {
                stringBuilder.append("Broken rule: ").append(ruleDefinition.getMessage()).append("\n");
                isValid = false;
            }
        }
        return isValid;
    }

    private boolean validateExtendedRules(StringBuilder stringBuilder) {
        var isValid = true;
        for (final var biRuleDefinition : xRuleDefinitions) {
            if (!biRuleDefinition.test(value, argument)) {
                stringBuilder.append("Broken rule: ")
                        .append(biRuleDefinition.getMessage())
                        .append("\n");
                isValid = false;
            }
        }
        return isValid;
    }

    @Override
    public <R> ReturningValidationBuilder<R> andThen(final Supplier<R> supplier) {
        return new EndingRuleValidationBuilder<>(supplier, this);
    }

    @Override
    public VoidEndingValidationBuilder andThen(final Runnable runnable) {
        return new VoidEndingRuleValidatorBuilder<>(runnable, this);
    }


    @Override
    public <E extends RuntimeException> Boolean orElseThrow(final Function<String, E> throwableFunction) {
        final var isValid = validate();
        if (!isValid) {
            throw throwableFunction.apply(getMessage());
        }
        return true;
    }

    @Override
    public T orElseReturn(final T other) {
        final var isValid = validate();
        if (!isValid) {
            return other;
        }
        return value;
    }

    @Override
    public T orElseReturn(final Function<String, T> other) {
        final var isValid = validate();
        if (!isValid) {
            return other.apply(message);
        }
        return value;
    }

}

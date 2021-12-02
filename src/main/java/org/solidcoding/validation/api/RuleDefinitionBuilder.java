package org.solidcoding.validation.api;

import java.util.function.Predicate;

final class RuleDefinitionBuilder<T> implements RuleBuilder<T> {

    private final Predicate<T> rule;

    public RuleDefinitionBuilder(Predicate<T> rule) {
        this.rule = rule;
    }

    public RuleBuilder<T> and(Predicate<T> rule) {
        return this;
    }

    public Rule<T> otherWiseReport(String failMessage, Object... formatArguments) {
        if (formatArguments != null) {
            var actualMessage = String.format(failMessage, formatArguments);
            return new RuleDefinition<>(rule, actualMessage);
        }
        return new RuleDefinition<>(rule, failMessage);
    }

}

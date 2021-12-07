package org.solidcoding.validation.api;

import java.util.function.Predicate;

final class RuleDefinitionBuilder<T> implements RuleBuilder<T> {

    private final Predicate<T> predicate;

    RuleDefinitionBuilder(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    public Rule<T> otherWiseReport(String failMessage, Object... formatArguments) {
        if (formatArguments != null) {
            var actualMessage = String.format(failMessage, formatArguments);
            return new RuleDefinition<>(predicate, actualMessage);
        }
        return new RuleDefinition<>(predicate, failMessage);
    }

}

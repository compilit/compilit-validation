package org.solidcoding.validation.api;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class RuleDefinitionBuilder<T> implements RuleBuilder<T> {

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

    public static final class Extended<T> implements RuleBuilder.Extended<T> {
        private final BiPredicate<T, Object> predicate;

        public Extended(BiPredicate<T, Object> predicate) {
            this.predicate = predicate;
        }

        @Override
        public Rule.Extended<T> otherWiseReport(String failMessage, Object... formatArguments) {
            if (formatArguments != null) {
                var actualMessage = String.format(failMessage, formatArguments);
                return new RuleDefinition.Extended<>(predicate, actualMessage);
            }
            return new RuleDefinition.Extended<>(predicate, failMessage);
        }

    }
}

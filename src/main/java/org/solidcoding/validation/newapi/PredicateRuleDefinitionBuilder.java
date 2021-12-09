package org.solidcoding.validation.newapi;

import org.solidcoding.validation.api.Rule;
import org.solidcoding.validation.api.RuleBuilder;
import org.solidcoding.validation.api.RuleDefinition;

public class PredicateRuleDefinitionBuilder<T> implements RuleBuilder<T> {

    private final GenericPredicateRule<T> predicate;

    public PredicateRuleDefinitionBuilder(GenericPredicateRule<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public Rule<T> otherWiseReport(String failMessage, Object... formatArguments) {
        return new RuleDefinition<>(predicate.getPredicate(), failMessage);
    }

    public static final class Extended<T> implements RuleBuilder.Extended<T> {

        private final GenericPredicateRule<T> predicate;

        public Extended(GenericPredicateRule<T> predicate) {
            this.predicate = predicate;
        }

        @Override
        public Rule.Extended<T> otherWiseReport(String failMessage, Object... formatArguments) {
            return new RuleDefinition.Extended<>(predicate.getBiPredicate(), failMessage);
        }
    }

}

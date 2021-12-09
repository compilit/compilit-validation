package org.solidcoding.validation.newapi;

import org.solidcoding.validation.api.ArgumentValidationBuilder;
import org.solidcoding.validation.api.ContinuingValidationBuilder;
import org.solidcoding.validation.api.Rule;

import java.util.List;

public class BiRuleArgumentValidationBuilder<T> implements ArgumentValidationBuilder<T> {

    private final List<Rule.Extended<T>> ruleDefinitions;
    private final T value;

    public BiRuleArgumentValidationBuilder(List<Rule.Extended<T>> ruleDefinitions, T value) {
        this.ruleDefinitions = ruleDefinitions;
        this.value = value;
    }

    public ContinuingValidationBuilder<T> whileApplying(Object argument) {
        return new ContinuingRuleValidationBuilder<>(ruleDefinitions, value, argument);
    }
}

package org.solidcoding.validation.api;

import org.solidcoding.validation.api.*;
import org.solidcoding.validation.newapi.BiRuleArgumentValidationBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class RuleValidationBuilder<T> implements ValidationBuilder<T> {

    private final T value;
    private final List<Rule<T>> ruleDefinitions;
    private final List<Rule.Extended<T>> xRuleDefinitions;

    public RuleValidationBuilder(T value) {
        this.value = value;
        this.ruleDefinitions = new ArrayList<>();
        this.xRuleDefinitions = new ArrayList<>();
    }

    @Override
    public ContinuingValidationBuilder<T> compliesWith(Rule<T> rule) {
        ruleDefinitions.add(rule);
        return new ContinuingRuleValidationBuilder<>(ruleDefinitions, value);
    }

    @Override
    public ArgumentValidationBuilder<T> compliesWith(Rule.Extended<T> rule) {
        xRuleDefinitions.add(rule);
        return new BiRuleArgumentValidationBuilder<T>(xRuleDefinitions, value);
    }

    @Override
    public ContinuingValidationBuilder<T> compliesWith(Collection<Rule<T>> rules) {
        ruleDefinitions.addAll(rules);
        return new ContinuingRuleValidationBuilder<>(ruleDefinitions, value);
    }

}

package org.solidcoding.validation.api;

import java.util.ArrayList;
import java.util.List;

final class RuleValidationBuilder<T> implements ValidationBuilder<T> {

    private final T value;
    private final List<Rule<T>> ruleDefinitions;
    private String message = "Nothing to report";

    public RuleValidationBuilder(T value) {
        this.value = value;
        this.ruleDefinitions = new ArrayList<>();
    }

    @Override
    public ContinuingValidator<T> compliesWith(Rule<T> rule) {
        ruleDefinitions.add(rule);
        return new ContinuingPredicateValidator<>(ruleDefinitions, value);
    }

}

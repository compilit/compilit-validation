package org.solidcoding.validation.api;

public interface ValidationBuilder<T> {

    /**
     * @param rule the rule which the value needs to comply with.
     * @return the Validator to add more rules.
     */
    ContinuingValidator<T> compliesWith(Rule<T> rule);

}

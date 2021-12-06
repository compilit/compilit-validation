package org.solidcoding.validation.api;

import java.util.Collection;

public interface ValidationBuilder<T> {

    /**
     * @param rule the rule which the value needs to comply with.
     * @return the Validator to add more rules.
     */
    ContinuingValidator<T> compliesWith(Rule<T> rule);

    /**
     * @param rules the rules which the value needs to comply with.
     * @return the Validator to add more rules.
     */
    ContinuingValidator<T> compliesWith(Collection<Rule<T>> rules);

}

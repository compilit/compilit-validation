package org.solidcoding.validation.newapi;

import org.solidcoding.validation.api.RuleValidationBuilder;
import org.solidcoding.validation.api.ValidationBuilder;

public final class MakeSure {

    private MakeSure() {
    }

    /**
     * @param value the value on which to apply the rules.
     * @param <T>   the type of the value.
     * @return a Validator to add rules to.
     */
    public static <T> ValidationBuilder<T> that(T value) {
        return new RuleValidationBuilder<>(value);
    }

}

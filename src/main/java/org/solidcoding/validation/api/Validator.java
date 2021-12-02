package org.solidcoding.validation.api;

public final class Validator {

    private Validator() {
    }

    /**
     * @param value the value on which to apply the rules.
     * @param <T>   the type of the value.
     * @return a Validator to add rules to.
     */
    public static <T> ValidationBuilder<T> makeSure(T value) {
        return new RuleValidationBuilder<T>(value);
    }
}

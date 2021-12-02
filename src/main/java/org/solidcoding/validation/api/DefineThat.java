package org.solidcoding.validation.api;

import java.util.function.Predicate;

public final class DefineThat {

    private DefineThat() {
    }

    public static <T> RuleBuilder<T> itShould(Predicate<T> rule) {
        return new RuleDefinitionBuilder<>(rule);
    }

}

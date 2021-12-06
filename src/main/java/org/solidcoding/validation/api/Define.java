package org.solidcoding.validation.api;

import java.util.function.Predicate;

public final class Define {

    private Define() {
    }

    public static <T> RuleBuilder<T> thatIt(Predicate<T> rule) {
        return new RuleDefinitionBuilder<>(rule);
    }

}

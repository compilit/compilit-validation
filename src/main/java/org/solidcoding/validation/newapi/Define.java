package org.solidcoding.validation.newapi;

import org.solidcoding.validation.api.Rule;
import org.solidcoding.validation.api.RuleBuilder;
import org.solidcoding.validation.api.RuleDefinitionBuilder;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class Define {

    private Define() {
    }

    public static <T> RuleBuilder<T> thatIt(Predicate<T> predicate) {
        return new RuleDefinitionBuilder<>(predicate);
    }

    public static <T> RuleBuilder.Extended<T> thatIt(BiPredicate<T, Object> predicate) {
        return new RuleDefinitionBuilder.Extended<>(predicate);
    }

    public static <T> RuleBuilder<Rule.Extended<T>> thatIt(GenericPredicateRule<T> predicate) {
        return new PredicateRuleDefinitionBuilder.Extended<T>(predicate);
    }

//    public static <T> GenericBiPredicate<T> thatItIsA(Class<T> clazz) {
//        return new ObjectBiPredicateBuilder<>();
//    }
//
//    public static <T> GenericBiPredicate<T> thatItIsAn(Class<T> clazz) {
//        return new ObjectBiPredicateBuilder<>();
//    }
}

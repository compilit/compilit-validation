package org.solidcoding.validation.api;

import java.util.function.Predicate;

public interface RuleBuilder<T> {

    RuleBuilder<T> and(Predicate<T> rule);

    Rule<T> otherWiseReport(String failMessage, Object... formatArguments);

}

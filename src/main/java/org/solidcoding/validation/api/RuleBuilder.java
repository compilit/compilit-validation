package org.solidcoding.validation.api;

public interface RuleBuilder<T> {

    Rule<T> otherWiseReport(String failMessage, Object... formatArguments);

}

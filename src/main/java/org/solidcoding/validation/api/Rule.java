package org.solidcoding.validation.api;

import java.util.function.Predicate;

public interface Rule<T> extends Predicate<T> {
    String getFailMessage();
}

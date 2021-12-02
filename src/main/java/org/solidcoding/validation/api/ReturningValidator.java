package org.solidcoding.validation.api;

import java.util.function.Function;

public interface ReturningValidator<R> {

    /**
     * @param throwable the Exception that needs to be thrown when a rule is broken.
     * @param <E>       the bound of the Exception that needs to be thrown when a rule is broken.
     * @return R the return type
     */
    <E extends RuntimeException> R orElseThrow(E throwable);

    /**
     * @param other the backup/default return type if the validation fails.
     * @return R the return type.
     */
    R orElseReturn(R other);

    /**
     * @param other the backup/default return type if the validation fails with the optional message that is contained in the Validator.
     * @return R the return type.
     */
    R orElseReturn(Function<String, R> other);
}

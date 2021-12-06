package org.solidcoding.validation.api;

import java.util.function.Function;

public interface ReturningValidator<R> extends ThrowingValidator<R>, LoggingValidator {

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

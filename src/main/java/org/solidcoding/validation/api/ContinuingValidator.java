package org.solidcoding.validation.api;

import java.util.function.Function;
import java.util.function.Supplier;

public interface ContinuingValidator<T> {

    /**
     * @return the failure message;
     */
    String getMessage();

    ContinuingValidator<T> and(Rule<T> rule);

    /**
     * @return boolean true if all rules pass. False if at least one rule fails.
     */
    boolean validate();

    /**
     * Same as validate(); but returns a custom object in the form of a supplier.
     *
     * @param supplier the supplier which encapsulated the return type.
     * @param <R>      the type you wish to return.
     * @return R in the form of a supplier.
     */
    <R> ReturningValidator<R> andThen(Supplier<R> supplier);

    /**
     * @param throwableFunction the function defining the Exception that needs to be thrown when a rule is broken. The String is the stored failure message of the validation.
     * @param <E>               the bound of the Exception that needs to be thrown when a rule is broken.
     * @return true if all rules pass.
     */
    <E extends RuntimeException> boolean orElseThrow(Function<String, E> throwableFunction);

    /**
     * @param other the backup/default return type if the validation fails.
     * @return T the return type.
     */
    T orElseReturn(T other);

    /**
     * @param other the backup/default return type if the validation fails with the optional message that is contained in the Validator.
     * @return T the return type.
     */
    T orElseReturn(Function<String, T> other);
}

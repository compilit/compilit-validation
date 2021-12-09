package org.solidcoding.validation.api;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface Rule<T> extends Predicate<T>{

    /**
     * @return the message containing information about the validation. Default to 'Nothing to report'.
     */
    String getMessage();

    interface Extended<T> extends BiPredicate<T, Object> {
        /**
         * @return the message containing information about the validation. Default to 'Nothing to report'.
         */
        String getMessage();
    }

}

package org.solidcoding.validation.predicates;

import java.util.Objects;
import java.util.function.Predicate;

final class ObjectPredicateBuilder<T> extends PredicateContainer<T> {

    private ObjectPredicateBuilder() {
        super();
    }

    private ObjectPredicateBuilder(Predicate<T> predicate) {
        super();
        addPredicate(predicate);
    }

    /**
     * Checks whether the actual value is present.
     *
     * @return Predicate to continue adding rules.
     */
    public static Predicate<String> isNotNull() {
        return new ObjectPredicateBuilder<>(Objects::nonNull);
    }

    /**
     * @param clazz the class of the object to validate. This is only a compiler flag to treat T as its instance.
     * @param <T>   the type upon which the validations are tested.
     * @return ObjectPredicate to continue adding rules.
     */
    public static <T> GenericPredicate<T> isA(Class<T> clazz) {
        return new ObjectPredicateBuilder<>();
    }

    /**
     * @param clazz the class of the object to validate. This is only a compiler flag to treat T as its instance.
     * @param <T>   the type upon which the validations are tested.
     * @return ObjectPredicate to continue adding rules.
     */
    public static <T> GenericPredicate<T> isAn(Class<T> clazz) {
        return isA(clazz);
    }

}

package org.solidcoding.validation.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class ObjectPredicate<T> implements Predicate<T> {

    private final List<Predicate<T>> rules = new ArrayList<>();

    private ObjectPredicate() {
    }

    private ObjectPredicate(Predicate<T> rule) {
        this.rules.add(rule);
    }

    /**
     * @param clazz the class of the object to validate. This is only a compiler flag to treat T as its instance.
     * @param <T>   the type upon which the validations are tested.
     * @return ObjectPredicate to continue adding rules.
     */
    public static <T> ObjectPredicate<T> beA(Class<T> clazz) {
        return new ObjectPredicate<>();
    }

    /**
     * @param clazz the class of the object to validate. This is only a compiler flag to treat T as its instance.
     * @param <T>   the type upon which the validations are tested.
     * @return ObjectPredicate to continue adding rules.
     */
    public static <T> ObjectPredicate<T> beAn(Class<T> clazz) {
        return beA(clazz);
    }

    /**
     * @param rule the custom predicate to test against properties of T.
     * @return ObjectPredicate to continue adding rules.
     */
    public ObjectPredicate<T> that(Predicate<T> rule) {
        return new ObjectPredicate<>(rule);
    }

    /**
     * @param rule the custom predicate to test against properties of T.
     * @return ObjectPredicate to continue adding rules.
     */
    public ObjectPredicate<T> where(Predicate<T> rule) {
        return new ObjectPredicate<>(rule);
    }

    @Override
    public boolean test(T value) {
        return rules.stream().allMatch(x -> x.test(value));
    }

}

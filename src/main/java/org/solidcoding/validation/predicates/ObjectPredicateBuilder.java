package org.solidcoding.validation.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ObjectPredicateBuilder<T> implements GenericPredicate<T> {

    private final List<Predicate<T>> predicates = new ArrayList<>();

    protected ObjectPredicateBuilder() {
    }

    protected ObjectPredicateBuilder(Predicate<T> predicate) {
        predicates.add(predicate);
    }

    protected ObjectPredicateBuilder(List<Predicate<T>> predicates) {
        this.predicates.addAll(predicates);
    }

    /**
     * Checks whether the actual value is present.
     * @param <T> the type upon which the validations are tested.
     * @return Predicate to continue adding rules.
     */
    public static <T> Predicate<T> isNotNull() {
        return new ObjectPredicateBuilder<>(Objects::nonNull);
    }

    /**
     * @param clazz the class of the object to validate. This is only a compiler flag to treat T as its instance.
     * @param <T>   the type upon which the validations are tested.
     * @return GenericPredicate to continue adding rules.
     */
    @SuppressWarnings("unused")
    public static <T> GenericPredicate<T> isA(Class<T> clazz) {
        return new ObjectPredicateBuilder<>();
    }

    /**
     * @param clazz the class of the object to validate. This is only a compiler flag to treat T as its instance.
     * @param <T>   the type upon which the validations are tested.
     * @return GenericPredicate to continue adding rules.
     */
    public static <T> GenericPredicate<T> isAn(Class<T> clazz) {
        return isA(clazz);
    }

    /**
     * Check if the actual value is equal to the given one.
     *
     * @param value the exact expected value.
     * @return Predicate to continue adding rules.
     */
    public static <T> Predicate<T> isEqualTo(T value) {
        return new ObjectPredicateBuilder<>(x -> Objects.equals(x, value));
    }

    @Override
    public boolean test(T value) {
        return predicates.stream().allMatch(x -> x.test(value));
    }

    @Override
    public Predicate<T> where(Predicate<T> predicate) {
        addPredicate(predicate);
        return this;
    }

    static <T> GenericPredicate<T> contains(Object value, Object... values) {
        var rules = new ArrayList<Predicate<T>>();
        rules.add(x -> x.toString().contains(value.toString()));
        for (var v : values) {
            rules.add(x -> x.toString().contains(v.toString()));
        }
        return new ObjectPredicateBuilder<>(rules);
    }

    static <T> GenericPredicate<T> doesNotContain(Object value, Object... values) {
        var rules = new ArrayList<Predicate<T>>();
        rules.add(x -> !x.toString().contains(value.toString()));
        for (var v : values) {
            rules.add(x -> !x.toString().contains(v.toString()));
        }
        return new ObjectPredicateBuilder<>(rules);
    }

    void addPredicate(Predicate<T> predicate) {
        predicates.add(predicate);
    }
}

package org.solidcoding.validation.newapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ObjectPredicateBuilder<T> implements GenericPredicateRule<T> {

    private final List<Predicate<T>> predicates = new ArrayList<>();
    private final List<BiPredicate<T, Object>> biPredicates = new ArrayList<>();

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
    public static <T> GenericPredicateRule<T> isNotNull() {
        return new ObjectPredicateBuilder<>(Objects::nonNull);
    }

    /**
     * @param clazz the class of the object to validate. This is only a compiler flag to treat T as its instance.
     * @param <T>   the type upon which the validations are tested.
     * @return GenericPredicateRule to continue adding rules.
     */
    @SuppressWarnings("unused")
    public static <T> GenericPredicateRule<T> isA(Class<T> clazz) {
        return new ObjectPredicateBuilder<>();
    }

    /**
     * @param clazz the class of the object to validate. This is only a compiler flag to treat T as its instance.
     * @param <T>   the type upon which the validations are tested.
     * @return GenericPredicateRule to continue adding rules.
     */
    public static <T> GenericPredicateRule<T> isAn(Class<T> clazz) {
        return isA(clazz);
    }

    /**
     * Check if the actual value is equal to the given one.
     *
     * @param value the exact expected value.
     * @return Predicate to continue adding rules.
     */
    public static <T> GenericPredicateRule<T> isEqualTo(T value) {
        return new ObjectPredicateBuilder<>(x -> Objects.equals(x, value));
    }

    @Override
    public boolean isPredicate() {
        return biPredicates.isEmpty() && !predicates.isEmpty();
    }

    @Override
    public boolean isBiPredicate() {
        return !biPredicates.isEmpty() && predicates.isEmpty();
    }

    @Override
    public Predicate<T> where(Predicate<T> predicate) {
        addPredicate(predicate);
        return getPredicate();
    }

    static <T> GenericPredicateRule<T> contains(Object value, Object... values) {
        var rules = new ArrayList<Predicate<T>>();
        rules.add(x -> x.toString().contains(value.toString()));
        for (var v : values) {
            rules.add(x -> x.toString().contains(v.toString()));
        }
        return new ObjectPredicateBuilder<>(rules);
    }

    static <T> GenericPredicateRule<T> doesNotContain(Object value, Object... values) {
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

    @Override
    public Predicate<T> getPredicate() {
        return value -> predicates.stream().allMatch(y -> y.test(value));
    }

    @Override
    public BiPredicate<T, Object> getBiPredicate() {
        return (value, argument) -> biPredicates.stream().allMatch((x) -> x.test(value, argument));
    }
}

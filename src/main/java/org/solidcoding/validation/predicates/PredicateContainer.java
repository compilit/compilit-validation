package org.solidcoding.validation.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

class PredicateContainer<T> implements GenericPredicate<T> {

    private final List<Predicate<T>> rules = new ArrayList<>();

    PredicateContainer() {
    }

    PredicateContainer(List<Predicate<T>> rules) {
        this.rules.addAll(rules);
    }

    void addPredicate(Predicate<T> predicate) {
        rules.add(predicate);
    }

    @Override
    public boolean test(T value) {
        return rules.stream().allMatch(x -> x.test(value));
    }

    @Override
    public Predicate<T> where(Predicate<T> rule) {
        addPredicate(rule);
        return this;
    }

    static <T> GenericPredicate<T> contains(Object value, Object... values) {
        var rules = new ArrayList<Predicate<T>>();
        rules.add(x -> x.toString().contains(value.toString()));
        for (var v : values) {
            rules.add(x -> x.toString().contains(v.toString()));
        }
        return new PredicateContainer<>(rules);
    }

    static <T> GenericPredicate<T> doesNotContain(Object value, Object... values) {
        var rules = new ArrayList<Predicate<T>>();
        rules.add(x -> !x.toString().contains(value.toString()));
        for (var v : values) {
            rules.add(x -> !x.toString().contains(v.toString()));
        }
        return new PredicateContainer<>(rules);
    }

}

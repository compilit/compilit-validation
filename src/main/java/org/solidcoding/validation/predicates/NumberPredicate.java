package org.solidcoding.validation.predicates;

import org.solidcoding.validation.api.ChainingPredicate;

import java.util.function.Predicate;

interface NumberPredicate extends GenericPredicate<Integer> {

    /**
     * @param first the first (inclusive) constraint. Can be either the high constraint or the low
     *              *               constraint.
     * @return a ChainingPredicate to add the second constraint.
     */
    ChainingPredicate<Integer, Predicate<Integer>> between(int first);

}

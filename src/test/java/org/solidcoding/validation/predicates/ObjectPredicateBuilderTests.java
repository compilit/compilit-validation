package org.solidcoding.validation.predicates;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.api.Define;
import org.solidcoding.validation.api.MakeSure;
import testutil.TestObject;

import static org.solidcoding.validation.predicates.ObjectPredicateBuilder.isA;
import static org.solidcoding.validation.predicates.ObjectPredicateBuilder.isAn;
import static org.solidcoding.validation.predicates.StringPredicateBuilder.isNotNull;

class ObjectPredicateBuilderTests {

    @Test
    void beA_String_shouldReturnObjectPredicate() {
        var rule = Define.thatIt(isA(String.class).where(x -> x.contains("test"))).otherWiseReport("failure");
        Assertions.assertThat(MakeSure.that("test").compliesWith(rule).validate()).isTrue();
    }

    @Test
    void beAn_Object_shouldReturnObjectPredicate() {
        var actualObject = new Object();
        var rule = Define.thatIt(isAn(Object.class).where(x -> x.equals(actualObject))).otherWiseReport("failure");
        Assertions.assertThat(MakeSure.that(actualObject).compliesWith(rule).validate()).isTrue();
    }

    @Test
    void beA_TestObjectWithoutValues_shouldReturnTrue() {
        var actualObject = new TestObject();
        actualObject.add("test");
        var rule = Define.thatIt(isA(TestObject.class).where(TestObject::hasValues)).otherWiseReport("failure");
        Assertions.assertThat(MakeSure.that(actualObject).compliesWith(rule).validate()).isTrue();
    }

    @Test
    void beA_TestObjectWithValues_shouldReturnOtherValue() {
        var failureMessage = "Something went wrong";
        var actualObject = new TestObject();
        var otherObject = new TestObject();
        var rule = Define.thatIt(isA(TestObject.class).where(TestObject::hasValues)).otherWiseReport(failureMessage);
        var rule2 = Define.thatIt(isA(TestObject.class).where(TestObject::hasValues)).otherWiseReport(failureMessage);
        Assertions.assertThat(MakeSure.that(actualObject).compliesWith(rule).and(rule2).orElseReturn(otherObject)).isEqualTo(otherObject);
        Assertions.assertThat(MakeSure.that(actualObject).compliesWith(rule).and(rule2).orElseReturn(otherObject)).isNotEqualTo(actualObject);
        Assertions.assertThat(MakeSure.that(actualObject).compliesWith(rule).and(rule2).orElseReturn(x -> otherObject)).isNotEqualTo(actualObject);
        Assertions.assertThat(MakeSure.that(actualObject).compliesWith(rule).and(rule2).orElseReturn(TestObject::new).getMessage()).contains(failureMessage);
    }

    @Test
    void isNotNull_shouldAddNotNullPredicate() {
        var rule = Define.thatIt(isNotNull()).otherWiseReport("failure");
        Assertions.assertThat(MakeSure.<String>that(null).compliesWith(rule).validate()).isFalse();
        Assertions.assertThat(MakeSure.that("test").compliesWith(rule).validate()).isTrue();
    }
}

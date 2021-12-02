package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import testutil.TestObject;

import static org.solidcoding.validation.predicates.ObjectPredicate.beA;
import static org.solidcoding.validation.predicates.ObjectPredicate.beAn;

class ObjectPredicateTests {

    @Test
    void beA_String_shouldReturnObjectPredicate() {
        var rule = DefineThat.itShould(beA(String.class).that(x -> x.contains("test"))).otherWiseReport("failure");
        Assertions.assertThat(Validator.makeSure("test").compliesWith(rule).validate()).isTrue();
    }

    @Test
    void beAn_Object_shouldReturnObjectPredicate() {
        var actualObject = new Object();
        var rule = DefineThat.itShould(beAn(Object.class).that(x -> x.equals(actualObject))).otherWiseReport("failure");
        Assertions.assertThat(Validator.makeSure(actualObject).compliesWith(rule).validate()).isTrue();
    }

    @Test
    void beA_TestObjectWithoutValues_shouldReturnTrue() {
        var actualObject = new TestObject();
        actualObject.add("test");
        var rule = DefineThat.itShould(beA(TestObject.class).that(x -> x.hasValues())).otherWiseReport("failure");
        Assertions.assertThat(Validator.makeSure(actualObject).compliesWith(rule).validate()).isTrue();
    }

    @Test
    void beA_TestObjectWithValues_shouldReturnOtherValue() {
        var failureMessage = "Something went wrong";
        var actualObject = new TestObject();
        var otherObject = new TestObject();
        var rule = DefineThat.itShould(beA(TestObject.class).that(x -> x.hasValues())).otherWiseReport(failureMessage);
        Assertions.assertThat(Validator.makeSure(actualObject).compliesWith(rule).orElseReturn(otherObject)).isEqualTo(otherObject);
        Assertions.assertThat(Validator.makeSure(actualObject).compliesWith(rule).orElseReturn(otherObject)).isNotEqualTo(actualObject);
        Assertions.assertThat(Validator.makeSure(actualObject).compliesWith(rule).orElseReturn(x -> otherObject)).isNotEqualTo(actualObject);
        Assertions.assertThat(Validator.makeSure(actualObject).compliesWith(rule).orElseReturn(TestObject::new).getMessage()).contains(failureMessage);
    }
}

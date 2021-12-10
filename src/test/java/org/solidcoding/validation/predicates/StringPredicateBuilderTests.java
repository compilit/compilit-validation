package org.solidcoding.validation.predicates;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.api.Define;
import org.solidcoding.validation.api.Verify;

import java.util.function.Supplier;

import static org.solidcoding.validation.predicates.StringPredicate.*;
import static testutil.TestValue.TEST_CONTENT;

class StringPredicateBuilderTests {

    @Test
    void validate_stringMatchingRule_shouldReturnTrue() {
        var value = TEST_CONTENT;
        var rule0 = Define.thatIt(isEqualTo(TEST_CONTENT)).otherwiseReport("failure");
        var rule1 = Define.thatIt(contains(TEST_CONTENT, TEST_CONTENT)).otherwiseReport("failure");
        var rule2 = Define.thatIt(doesNotContain("?")).otherwiseReport("failure");
        var rule3 = Define.thatIt(hasALengthOf(TEST_CONTENT.length()).and(contains("t"))).otherwiseReport("failure");
        var rule4 = Define.thatIt(hasALengthBetween(0).and(10)).otherwiseReport("failure");
        var rule5 = Define.thatIt(isAlphabetic()).otherwiseReport("failure");
        var rule6 = Define.thatIt(isNotNumeric()).otherwiseReport("failure");
        var rule7 = Define.thatIt(isNotNull()).otherwiseReport("failure");
        Assertions.assertThat(Verify.that(value).compliesWith(rule0).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule2).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule3).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule4).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule5).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule6).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule7).validate()).isTrue();
    }

    @Test
    void validate_stringNotMatchingRule_shouldReturnFalse() {
        var value = TEST_CONTENT;
        var rule0 = Define.thatIt(isEqualTo("?")).otherwiseReport("failure");
        var rule1 = Define.thatIt(contains("?", "bla")).otherwiseReport("failure");
        var rule2 = Define.thatIt(doesNotContain(TEST_CONTENT, TEST_CONTENT)).otherwiseReport("failure");
        var rule3 = Define.thatIt(hasALengthOf(123).and(contains("t"))).otherwiseReport("failure");
        var rule4 = Define.thatIt(hasALengthBetween(100).and(200)).otherwiseReport("failure");
        var rule5 = Define.thatIt(isNotAlphabetic()).otherwiseReport("failure");
        var rule6 = Define.thatIt(isNumeric()).otherwiseReport("failure");
        var rule7 = Define.thatIt(isNotNull()).otherwiseReport("failure");
        Assertions.assertThat(Verify.that(value).compliesWith(rule0).validate()).isFalse();
        Assertions.assertThat(Verify.that(value).compliesWith(rule1).validate()).isFalse();
        Assertions.assertThat(Verify.that(value).compliesWith(rule2).validate()).isFalse();
        Assertions.assertThat(Verify.that(value).compliesWith(rule3).validate()).isFalse();
        Assertions.assertThat(Verify.that(value).compliesWith(rule4).validate()).isFalse();
        Assertions.assertThat(Verify.that(value).compliesWith(rule5).validate()).isFalse();
        Assertions.assertThat(Verify.that(value).compliesWith(rule6).validate()).isFalse();
        Assertions.assertThat(Verify.<String>that(null).compliesWith(rule7).validate()).isFalse();
    }

    @Test
    void andThen_stringMatchingRule_shouldReturnWantedObject() {
        var value = TEST_CONTENT;
        var rule0 = Define.thatIt(isEqualTo("test")).otherwiseReport("failure");
        Supplier<String> supplier = () -> value;
        Assertions.assertThat(Verify.that(value).compliesWith(rule0).andThen(supplier).orElseThrow(RuntimeException::new)).isEqualTo(value);
    }

    @Test
    void andThen_stringNotMatchingRule_shouldReturnOtherObject() {
        var value = TEST_CONTENT;
        var otherValue = TEST_CONTENT + TEST_CONTENT;
        var rule0 = Define.thatIt(isEqualTo("blah")).otherwiseReport("failure");
        Supplier<String> supplier = () -> value;
        Assertions.assertThat(Verify.that(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isEqualTo(otherValue);
        Assertions.assertThat(Verify.that(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isNotEqualTo(value);
    }

}

package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.solidcoding.validation.predicates.StringPredicate.*;
import static testutil.TestValue.TEST_CONTENT;

class StringPredicateTests {

    @Test
    void itShould_stringMatchingRule_shouldReturnTrue() {
        var value = TEST_CONTENT;
        var rule0 = DefineThat.itShould(beAString("test")).otherWiseReport("failure");
        var rule1 = DefineThat.itShould(beAString().containing("test")).otherWiseReport("failure");
        var rule2 = DefineThat.itShould(beAStringWithLength(4).containing("t")).otherWiseReport("failure");
        var rule3 = DefineThat.itShould(beAString(with -> with.length() == 4)).otherWiseReport("failure");
        var rule4 = DefineThat.itShould(beAlphabetic()).otherWiseReport("failure");
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule4).validate()).isTrue();
    }

    @Test
    void andThen_stringMatchingRule_shouldReturnWantedObject() {
        var value = TEST_CONTENT;
        var rule0 = DefineThat.itShould(beAString("test")).otherWiseReport("failure");
        Supplier<String> supplier = () -> value;
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).andThen(supplier).orElseThrow(new RuntimeException())).isEqualTo(value);
    }

    @Test
    void andThen_stringNotMatchingRule_shouldReturnOtherObject() {
        var value = TEST_CONTENT;
        var otherValue = TEST_CONTENT + TEST_CONTENT;
        var rule0 = DefineThat.itShould(beAString("blah")).otherWiseReport("failure");
        Supplier<String> supplier = () -> value;
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isEqualTo(otherValue);
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isNotEqualTo(value);
    }

    @Test
    void itShould_stringNotMatchingRule_shouldReturnFalse() {
        var value = TEST_CONTENT;
        var rule0 = DefineThat.itShould(beAString("")).otherWiseReport("failure");
        var rule1 = DefineThat.itShould(beAString().containing("1")).otherWiseReport("failure");
        var rule2 = DefineThat.itShould(beAStringWithLength(40).containing("54353")).otherWiseReport("failure");
        var rule3 = DefineThat.itShould(beAString(with -> with.length() == 40)).otherWiseReport("failure");
        var rule4 = DefineThat.itShould(beNumeric()).otherWiseReport("failure");
        var rule5 = DefineThat.itShould(beAlphabetic()).otherWiseReport("failure");
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isFalse();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isFalse();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isFalse();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isFalse();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule4).validate()).isFalse();
        Assertions.assertThat(Validator.makeSure("123465").compliesWith(rule5).validate()).isFalse();
    }
}
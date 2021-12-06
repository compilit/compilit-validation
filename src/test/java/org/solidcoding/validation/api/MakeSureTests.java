package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.solidcoding.validation.predicates.StringPredicateBuilder.contains;
import static org.solidcoding.validation.predicates.StringPredicateBuilder.hasALengthOf;
import static testutil.TestValue.TEST_CONTENT;

class MakeSureTests {

    @Test
    void makeSure_shouldReturnNewValidatorEntryPoint() {
        Assertions.assertThat(MakeSure.that(TEST_CONTENT)).isInstanceOf(ValidationBuilder.class);
    }

    @Test
    void completeRule_formattedMessage_shouldReturnFormattedMessage() {
        var ruleFailMessage = "%s does not contain 123!";
        var value = "4";
        var rule1 = Define.thatIt(contains("123")).otherWiseReport(ruleFailMessage, value);
        var rule2 = Define.thatIt(hasALengthOf(1)).otherWiseReport(ruleFailMessage, value);
        var expectedMessage = String.format(ruleFailMessage, value);
        var validator = MakeSure.that(value).compliesWith(rule1).and(rule2);
        validator.validate();
        var actual = validator.getMessage();
        Assertions.assertThat(actual).contains(expectedMessage);
    }

}

package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.solidcoding.validation.predicates.StringPredicate.shouldBeAString;
import static org.solidcoding.validation.predicates.StringPredicate.shouldBeAStringWithLength;
import static testutil.TestValue.TEST_CONTENT;

class ValidatorTests {

    @Test
    void makeSure_shouldReturnNewValidatorEntryPoint() {
        Assertions.assertThat(Validator.makeSure(TEST_CONTENT)).isInstanceOf(ValidationBuilder.class);
    }

    @Test
    void compliesWith_validInput_shouldReturnValidator() {
        var rule = new RuleDefinition<String>(x -> true, TEST_CONTENT);
        Assertions.assertThat(Validator.makeSure(TEST_CONTENT).compliesWith(rule))
                .isInstanceOf(ContinuingValidator.class);
    }

    @Test
    void compliesWith_invalidInput_shouldReturnValidator() {
        var rule = new RuleDefinition<String>(x -> false, TEST_CONTENT);
        Assertions.assertThat(Validator.makeSure(TEST_CONTENT).compliesWith(rule))
                .isInstanceOf(ContinuingValidator.class);
    }

    @Test
    void orElse_validInput_shouldReturnTrue() {
        var rule = new RuleDefinition<String>(x -> true, TEST_CONTENT);
        Assertions.assertThat(Validator.makeSure(TEST_CONTENT)
                .compliesWith(rule)
                .orElseThrow(RuntimeException::new)).isTrue();
    }

    @Test
    void orElse_invalidInput_shouldThrowGivenAException() {
        var rule = new RuleDefinition<String>(x -> false, TEST_CONTENT);
        Assertions.assertThatThrownBy(() -> Validator.makeSure(TEST_CONTENT)
                        .compliesWith(rule)
                        .orElseThrow(RuntimeException::new))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void completeRule_formattedMessage_shouldReturnFormattedMessage() {
        var ruleFailMessage = "%s does not contain 123!";
        var value = "4";
        var rule1 = DefineThat.it(shouldBeAString().containing("123")).otherWiseReport(ruleFailMessage, value);
        var rule2 = DefineThat.it(shouldBeAStringWithLength(1)).otherWiseReport(ruleFailMessage, value);
        var expectedMessage = String.format(ruleFailMessage, value);
        var validator = Validator.makeSure(value).compliesWith(rule1).and(rule2);
        validator.validate();
        var actual = validator.getMessage();
        Assertions.assertThat(actual).contains(expectedMessage);
    }

}

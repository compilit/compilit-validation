package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testutil.AbstractTestWithContext;
import testutil.TestObject;

import static org.solidcoding.validation.api.ContinuingRuleValidationBuilder.DEFAULT_MESSAGE;
import static org.solidcoding.validation.api.Definitions.*;
import static org.solidcoding.validation.api.Verifications.*;
import static org.solidcoding.validation.predicates.ObjectPredicate.isA;
import static org.solidcoding.validation.predicates.StringPredicate.contains;
import static org.solidcoding.validation.predicates.StringPredicate.hasALengthBetween;
import static org.solidcoding.validation.predicates.StringPredicate.isAlphabetic;
import static org.solidcoding.validation.predicates.StringPredicate.isEqualTo;
import static org.solidcoding.validation.predicates.StringPredicate.isNotNumeric;

class ApiUsageExampleTests extends AbstractTestWithContext {

    private final String passwordRuleFailMessage = "Password did not meet our requirements!";
    private final String goodPassword = "#This%Should*BeAGoodPassword(";
    private final String badPassword = "Whatever";
    private final Rule<String> passwordRule = defineThatIt(contains("#", "%", "*", "(")
            .and(hasALengthBetween(15).and(50))).otherwiseReport(passwordRuleFailMessage);

    @BeforeEach
    public void reset() {
        super.reset();
    }

    @Test
    void validate_goodPassword_shouldReturnTrue() {
        var passwordRuleFailMessage = "Password did not meet our requirements!";

        var passwordRule = defineThatIt(contains("#", "%", "*", "(")
                .and(hasALengthBetween(15).and(50))).otherwiseReport(passwordRuleFailMessage);

        Assertions.assertThat(verifyThat(goodPassword)
                        .compliesWith(passwordRule)
                        .validate())
                .isTrue();
    }

    @Test
    void orElseThrow_goodPassword_shouldNotThrowException() {
        Assertions.assertThatNoException().isThrownBy(
                () -> verifyThat(goodPassword)
                        .compliesWith(passwordRule)
                        .orElseThrow(RuntimeException::new));
    }

    @Test
    void validate_badPassword_shouldReturnFalse() {
        Assertions.assertThat(verifyThat(badPassword)
                .compliesWith(passwordRule)
                .validate()).isFalse();
    }

    @Test
    void orElseThrow_badPassword_shouldThrowException() {
        Assertions.assertThatThrownBy(() -> verifyThat(badPassword)
                        .compliesWith(passwordRule)
                        .orElseThrow(RuntimeException::new))
                .hasMessageContaining(passwordRuleFailMessage);
    }

    @Test
    void orElseReturn_badPassword_shouldReturnFailMessage() {
        Assertions.assertThat(verifyThat(badPassword)
                        .compliesWith(passwordRule)
                        .orElseReturn(message -> message))
                .contains(passwordRuleFailMessage);
    }

    @Test
    void useCase_alphabeticInputValidation_shouldNotThrowException() {
        var alphabeticStringRule1 = defineThatIt(isAlphabetic()).otherwiseReport("It's not alphabetic");
        var alphabeticStringRule2 = defineThatIt(isNotNumeric()).otherwiseReport("It's not alphabetic");
        var alphabeticStringRule3 = defineThatIt(isAlphabetic().and(isNotNumeric())).otherwiseReport("It's not alphabetic");
        Assertions.assertThatNoException().isThrownBy(
                () -> verifyThat("test").compliesWith(alphabeticStringRule1)
                        .and(alphabeticStringRule2)
                        .and(alphabeticStringRule3)
                        .andThen(super::interact)
                        .orElseThrow(RuntimeException::new));
        Assertions.assertThat(hasBeenInteractedWith()).isTrue();
    }

    @Test
    void useCase_validateAgainstValidOtherInput_shouldNotThrowException() {
        var rule = defineThatIt(isA(TestObject.class).where((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");
        var rule2 = defineThatIt(isA(TestObject.class).that((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");

        Assertions.assertThatNoException().isThrownBy(() -> verifyThat(new TestObject()).compliesWith(rule)
                .whileApplying(DEFAULT_MESSAGE)
                .and(rule2)
                .andThen(super::interact)
                .orElseThrow(RuntimeException::new));
        Assertions.assertThat(hasBeenInteractedWith()).isTrue();
    }

    @Test
    void useCase_validateAgainstInvalidOtherInput_shouldThrowException() {
        var rule = defineThatIt(isA(TestObject.class).where((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");
        var rule2 = defineThatIt(isA(TestObject.class).that((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");
        Assertions.assertThatThrownBy(() -> verifyThat(new TestObject()).compliesWith(rule)
                .whileApplying("This is not what I would expect")
                .and(rule2)
                .andThen(super::interact)
                .orElseThrow(RuntimeException::new)).isInstanceOf(RuntimeException.class);
        Assertions.assertThat(hasBeenInteractedWith()).isFalse();
    }

}

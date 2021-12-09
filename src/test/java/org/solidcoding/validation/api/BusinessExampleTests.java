package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.newapi.Define;
import testutil.TestObject;

import static org.solidcoding.validation.api.ContinuingRuleValidationBuilder.DEFAULT_MESSAGE;
import static org.solidcoding.validation.newapi.ObjectPredicateBuilder.isA;
import static org.solidcoding.validation.predicates.StringPredicateBuilder.contains;
import static org.solidcoding.validation.predicates.StringPredicateBuilder.hasALengthBetween;
import static org.solidcoding.validation.predicates.StringPredicateBuilder.isAlphabetic;
import static org.solidcoding.validation.predicates.StringPredicateBuilder.isNotNumeric;

class BusinessExampleTests {

    @Test
    void useCase_passwordEntry_shouldValidatePasswordConstraintsAndReturnExpectedResponse() {
        var passwordRuleFailMessage = "Password did not meet our requirements!";
        var goodPassword = "#This%Should*BeAGoodPassword(";
        var badPassword = "Whatever";
        var passwordRule = Define.thatIt(contains("#", "%", "*", "(")
                .and(hasALengthBetween(15).and(50))).otherWiseReport(passwordRuleFailMessage);

        Assertions.assertThat(MakeSure.that(goodPassword)
                .compliesWith(passwordRule)
                .validate()).isTrue();
        Assertions.assertThat(MakeSure.that(badPassword)
                        .compliesWith(passwordRule)
                        .orElseReturn(failMessage -> failMessage))
                .contains(passwordRuleFailMessage);

        Assertions.assertThatThrownBy(() -> MakeSure.that(badPassword)
                .compliesWith(passwordRule)
                .orElseThrow(RuntimeException::new)).hasMessageContaining(passwordRuleFailMessage);
    }

    @Test
    void useCase_alphabeticInputValidation_shouldNotThrowException() {
        Assertions.assertThatNoException().isThrownBy(
                () -> MakeSure.that("test").compliesWith(Define.thatIt(isAlphabetic().and(isNotNumeric())).otherWiseReport("It's not alphabetic"))
                        .andThen(() -> System.out.println("yay")).orElseThrow(RuntimeException::new));
    }

    @Test
    void useCase_validBiPredicate_shouldNotThrowException() {
        var rule = Define.thatIt(isA(TestObject.class).where((x, y) -> x.getMessage().equals(y))).otherWiseReport("failure");
        Assertions.assertThatNoException().isThrownBy(() -> MakeSure.that(new TestObject()).compliesWith(rule)
                .whileApplying(DEFAULT_MESSAGE)
                .andThen(() -> System.out.println("yay"))
                .orElseThrow(RuntimeException::new));
    }

    @Test
    void useCase_invalidBiPredicate_shouldThrowException() {
        var rule = Define.thatIt(isA(TestObject.class).where((x, y) -> x.getMessage().equals(y))).otherWiseReport("failure");
        Assertions.assertThatThrownBy(() -> MakeSure.that(new TestObject()).compliesWith(rule)
                .whileApplying("This is not what I would expect")
                .andThen(() -> System.out.println("yay"))
                .orElseThrow(RuntimeException::new)).isInstanceOf(RuntimeException.class);
    }


}

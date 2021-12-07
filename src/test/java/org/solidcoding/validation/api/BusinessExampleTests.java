package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.solidcoding.validation.api.Define.thatIt;
import static org.solidcoding.validation.predicates.StringPredicateBuilder.*;

class BusinessExampleTests {

    @Test
    void useCase_passwordEntry_shouldValidatePasswordConstraintsAndReturnExpectedResponse() {
        var passwordRuleFailMessage = "Password did not meet our requirements!";
        var goodPassword = "#This%Should*BeAGoodPassword(";
        var badPassword = "Whatever";
        var passwordRule = thatIt(contains("#", "%", "*", "(")
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
                () -> MakeSure.that("test").compliesWith(thatIt(isAlphabetic().and(isNotNumeric())).otherWiseReport("It's not alphabetic"))
                        .andThen(() -> System.out.println("yay")).orElseThrow(RuntimeException::new));
    }


}

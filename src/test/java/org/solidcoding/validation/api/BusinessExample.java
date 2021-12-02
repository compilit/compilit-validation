package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.solidcoding.validation.predicates.StringPredicate.beAString;

class BusinessExample {

    @Test
    void useCase_passwordEntry_shouldValidatePasswordConstraintsAndReturnExpectedResponse() {
        var passwordRuleFailMessage = "Password did not meet our requirements!";
        var goodPassword = "#This%Should*BeAGoodPassword(";
        var badPassword = "Whatever";
        var passwordRule = DefineThat.itShould(beAString().containing("#", "%", "*", "(")).otherWiseReport(passwordRuleFailMessage);

        Assertions.assertThat(Validator.makeSure(goodPassword)
                .compliesWith(passwordRule)
                .validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(badPassword)
                        .compliesWith(passwordRule)
                        .orElseReturn(failMessage -> failMessage))
                .contains(passwordRuleFailMessage);

        Assertions.assertThatThrownBy(() -> Validator.makeSure(badPassword)
                .compliesWith(passwordRule)
                .orElseThrow(RuntimeException::new)).hasMessageContaining(passwordRuleFailMessage);

    }

}

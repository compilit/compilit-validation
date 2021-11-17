package org.solidcoding.validation;

import static org.solidcoding.validation.StringPredicate.beAString;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BusinessExample {

  @Test
  void useCase_passwordEntry_shouldValidatePasswordConstraintsAndReturnExpectedResponse() {
    var passwordRule = DefineThat.itShould(beAString().containing("#", "%", "*", "("));
    var passwordRuleFailMessage = "Password did not meet our requirements!";
    var actualPassword = "#This%Should*BeAGoodPassword(";
    var actualBadPassword = "Whatever";

    Assertions.assertThat(Validator.makeSure(actualPassword)
                                   .compliesWith(passwordRule)
                                   .validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(actualBadPassword)
                                   .compliesWith(passwordRule, passwordRuleFailMessage)
                                   .validate()).isFalse();
    Assertions.assertThat(Validator.makeSure(actualBadPassword)
                                   .compliesWith(passwordRule, passwordRuleFailMessage)
                                   .orElseReturn(passwordRuleFailMessage))
              .isEqualTo(passwordRuleFailMessage);
    Assertions.assertThat(Validator.makeSure(actualBadPassword)
                                   .compliesWith(passwordRule, passwordRuleFailMessage)
                                   .orElseReturn(failMessage -> failMessage))
              .isEqualTo(passwordRuleFailMessage);
  }

}

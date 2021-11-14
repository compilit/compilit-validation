package org.solidcoding.validation;

import static org.solidcoding.validation.StringPredicate.beAString;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BusinessExample {

  @Test
  void useCase_passwordEntry_shouldValidatePasswordConstraints() {
    var characterConstraints = new ArrayList<String>();
    characterConstraints.addAll(List.of());
    var passwordRule = DefineThat.itShould(beAString().containing("#", "%", "*", "("));

    var actualPassword = "#This%Should*BeAGoodPassword(";
    var actualBadPassword = "Whatever";

    Assertions.assertThat(Validator.makeSure(actualPassword).compliesWith(passwordRule).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(actualBadPassword).compliesWith(passwordRule).validate()).isFalse();
  }

}

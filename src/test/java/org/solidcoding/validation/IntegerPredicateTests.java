package org.solidcoding.validation;

import static org.solidcoding.validation.IntegerPredicate.beAnInteger;
import static org.solidcoding.validation.IntegerPredicate.beAnIntegerOfLength;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class IntegerPredicateTests {

  @Test
  void itShould_integerMatchingRule_shouldReturnTrue() {
    var value = 2;
    var rule0 = DefineThat.itShould(beAnInteger(2));
    var rule1 = DefineThat.itShould(beAnInteger().between(1, 5));
    var rule2 = DefineThat.itShould(beAnIntegerOfLength(1));
    var rule3 = DefineThat.itShould(beAnInteger(that -> that.equals(2)));
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isTrue();
  }

  @Test
  void itShould_integerNotMatchingRule_shouldReturnFalse() {
    var value = 2;
    var rule0 = DefineThat.itShould(beAnInteger(1));
    var rule1 = DefineThat.itShould(beAnInteger().between(5, 50));
    var rule2 = DefineThat.itShould(beAnIntegerOfLength(10));
    var rule3 = DefineThat.itShould(beAnInteger(that -> that.equals(5435)));
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isFalse();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isFalse();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isFalse();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isFalse();
  }
}

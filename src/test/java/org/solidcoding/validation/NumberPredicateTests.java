package org.solidcoding.validation;

import static org.solidcoding.validation.NumberPredicate.beANumber;
import static org.solidcoding.validation.NumberPredicate.beANumberOfLength;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NumberPredicateTests {

  @Test
  void itShould_integerMatchingRule_shouldReturnTrue() {
    var value = 2;
    var rule0 = DefineThat.itShould(beANumber(2));
    var rule1 = DefineThat.itShould(beANumber().between(1).and(5));
    var rule2 = DefineThat.itShould(beANumberOfLength(1));
    var rule3 = DefineThat.itShould(beANumber(that -> that.equals(2)));
    var rule4 = DefineThat.itShould(beANumber().containing(2));
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule4).validate()).isTrue();
  }

  @Test
  void itShould_integerNotMatchingRule_shouldReturnFalse() {
    var value = 2;
    var rule0 = DefineThat.itShould(beANumber(1));
    var rule1 = DefineThat.itShould(beANumber().between(5).and(50));
    var rule2 = DefineThat.itShould(beANumberOfLength(10));
    var rule3 = DefineThat.itShould(beANumber(that -> that.equals(5435)));
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isFalse();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isFalse();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isFalse();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isFalse();
  }

  @Test
  void itShould_extendedTests() {
    var value = 123456789;
    var rule1 = DefineThat.itShould(beANumber().containing(1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 23, 789));
    var rule2 = DefineThat.itShould(beANumber().containing(10,
                                                           11,
                                                           22,
                                                           33,
                                                           44,
                                                           55,
                                                           66,
                                                           77,
                                                           88,
                                                           9999));
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isFalse();
  }
}

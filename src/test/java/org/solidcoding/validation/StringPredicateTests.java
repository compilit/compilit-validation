package org.solidcoding.validation;

import static org.solidcoding.validation.StringPredicate.beAString;
import static org.solidcoding.validation.StringPredicate.beAStringWithLength;
import static org.solidcoding.validation.StringPredicate.beAlphabetic;
import static org.solidcoding.validation.StringPredicate.beNumeric;
import static org.solidcoding.validation.testutil.TestValue.TEST_CONTENT;

import java.util.function.Supplier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StringPredicateTests {

  @Test
  void itShould_stringMatchingRule_shouldReturnTrue() {
    var value = TEST_CONTENT;
    var rule0 = DefineThat.itShould(beAString("test"));
    var rule1 = DefineThat.itShould(beAString().containing("test"));
    var rule2 = DefineThat.itShould(beAStringWithLength(4).containing("t"));
    var rule3 = DefineThat.itShould(beAString(with -> with.length() == 4));
    var rule4 = DefineThat.itShould(beAlphabetic());
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isTrue();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule4).validate()).isTrue();
  }

  @Test
  void andThen_stringMatchingRule_shouldReturnWantedObject() {
    var value = TEST_CONTENT;
    var rule0 = DefineThat.itShould(beAString("test"));
    Supplier<String> supplier = () -> value;
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).andThen(supplier).orElseThrow(new RuntimeException())).isEqualTo(value);
  }

  @Test
  void andThen_stringNotMatchingRule_shouldReturnOtherObject() {
    var value = TEST_CONTENT;
    var otherValue = TEST_CONTENT + TEST_CONTENT;
    var rule0 = DefineThat.itShould(beAString("blah"));
    Supplier<String> supplier = () -> value;
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isEqualTo(otherValue);
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isNotEqualTo(value);
  }

  @Test
  void itShould_stringNotMatchingRule_shouldReturnFalse() {
    var value = TEST_CONTENT;
    var rule0 = DefineThat.itShould(beAString(""));
    var rule1 = DefineThat.itShould(beAString().containing("1"));
    var rule2 = DefineThat.itShould(beAStringWithLength(40).containing("54353"));
    var rule3 = DefineThat.itShould(beAString(with -> with.length() == 40));
    var rule4 = DefineThat.itShould(beNumeric());
    var rule5 = DefineThat.itShould(beAlphabetic());
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isFalse();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isFalse();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isFalse();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isFalse();
    Assertions.assertThat(Validator.makeSure(value).compliesWith(rule4).validate()).isFalse();
    Assertions.assertThat(Validator.makeSure("123465").compliesWith(rule5).validate()).isFalse();
  }
}

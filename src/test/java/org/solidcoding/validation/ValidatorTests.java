package org.solidcoding.validation;

import static org.solidcoding.validation.testutil.TestValue.TEST_CONTENT;

import java.util.ArrayList;
import java.util.function.Predicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidatorTests {

  @Test
  void makeSure_shouldReturnNewValidatorEntryPoint() {
    Assertions.assertThat(Validator.makeSure(TEST_CONTENT)).isInstanceOf(Validator.class);
  }

  @Test
  void compliesWith_validInput_shouldReturnValidator() {
    var rule = new Predicate<String>() {
      @Override
      public boolean test(String value) {
        return true;
      }
    };
    Assertions.assertThat(Validator.makeSure(TEST_CONTENT).compliesWith(rule))
              .isInstanceOf(Validator.class);
  }

  @Test
  void compliesWith_validListInput_shouldReturnValidator() {
    var rule1 = new Predicate<String>() {
      @Override
      public boolean test(String value) {
        return true;
      }
    };
    var rule2 = new Predicate<String>() {
      @Override
      public boolean test(String value) {
        return true;
      }
    };
    var ruleList = new ArrayList<Predicate<String>>();
    ruleList.add(rule1);
    ruleList.add(rule2);
    Assertions.assertThat(Validator.makeSure(TEST_CONTENT).compliesWith(ruleList))
              .isInstanceOf(Validator.class);
  }

  @Test
  void compliesWith_invalidInput_shouldReturnValidator() {
    var rule = new Predicate<String>() {
      @Override
      public boolean test(String value) {
        return false;
      }
    };
    Assertions.assertThat(Validator.makeSure(TEST_CONTENT).compliesWith(rule))
              .isInstanceOf(Validator.class);
  }

  @Test
  void orElse_validInput_shouldReturnTrue() {
    var rule = new Predicate<String>() {
      @Override
      public boolean test(String value) {
        return true;
      }
    };
    Assertions.assertThat(Validator.makeSure(TEST_CONTENT)
                                   .compliesWith(rule)
                                   .orElseThrow(new RuntimeException())).isTrue();
  }

  @Test
  void orElse_invalidInput_shouldThrowGivenAException() {
    var rule = new Predicate<String>() {
      @Override
      public boolean test(String value) {
        return false;
      }
    };
    Assertions.assertThatThrownBy(() -> Validator.makeSure(TEST_CONTENT)
                                                 .compliesWith(rule)
                                                 .orElseThrow(new RuntimeException()))
              .isInstanceOf(RuntimeException.class);
  }

}

package org.solidcoding.validation;

import static org.solidcoding.validation.testutil.TestValue.TEST_CONTENT;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidatorTests {

  @Test
  void makeSure_shouldReturnNewValidatorEntryPoint() {
    Assertions.assertThat(Validator.makeSure(TEST_CONTENT)).isInstanceOf(Validator.class);
  }

  @Test
  void compliesWith_validInput_shouldReturnValidator() {
    var rule = new Rule<String>() {
      @Override
      public boolean validate(String value) {
        return true;
      }
    };
    Assertions.assertThat(Validator.makeSure(TEST_CONTENT).compliesWith(rule))
              .isInstanceOf(Validator.class);
  }

  @Test
  void compliesWith_invalidInput_shouldReturnValidator() {
    var rule = new Rule<String>() {
      @Override
      public boolean validate(String value) {
        return false;
      }
    };
    Assertions.assertThat(Validator.makeSure(TEST_CONTENT).compliesWith(rule))
              .isInstanceOf(Validator.class);
  }

  @Test
  void orElse_validInput_shouldReturnTrue() {
    var rule = new Rule<String>() {
      @Override
      public boolean validate(String value) {
        return true;
      }
    };
    Assertions.assertThat(Validator.makeSure(TEST_CONTENT)
                                   .compliesWith(rule)
                                   .orElseThrow(RuntimeException::new)).isTrue();
  }

  @Test
  void orElse_invalidInput_shouldThrowGivenAException() {
    var rule = new Rule<String>() {
      @Override
      public boolean validate(String value) {
        return false;
      }
    };
    Assertions.assertThatThrownBy(() -> Validator.makeSure(TEST_CONTENT)
                                                 .compliesWith(rule)
                                                 .orElseThrow(RuntimeException::new))
              .isInstanceOf(RuntimeException.class);
  }

}

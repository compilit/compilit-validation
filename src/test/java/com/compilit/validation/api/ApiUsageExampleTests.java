package com.compilit.validation.api;

import com.compilit.validation.api.contracts.Rule;
import com.compilit.validation.predicates.ObjectPredicate;
import com.compilit.validation.predicates.StringPredicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testutil.AbstractTestWithContext;
import testutil.TestObject;

import static com.compilit.validation.api.Definitions.defineThatIt;
import static com.compilit.validation.api.Subject.DEFAULT_MESSAGE;

class ApiUsageExampleTests extends AbstractTestWithContext {

  private final String passwordRuleFailMessage = "Password did not meet our requirements!";
  private final String goodPassword = "#This%Should*BeAGoodPassword(";
  private final String badPassword = "Whatever";
  private final Rule<String> passwordRule = Definitions.defineThatIt(StringPredicate.contains("#", "%", "*", "(")
          .and(StringPredicate.hasALengthBetween(15).and(50))).otherwiseReport(passwordRuleFailMessage);

  @BeforeEach
  public void reset() {
    super.reset();
  }

  @Test
  void validate_goodPassword_shouldReturnTrue() {
    var passwordRuleFailMessage = "Password did not meet our requirements!";

    var passwordRule = Definitions.defineThatIt(StringPredicate.contains("#", "%", "*", "(")
            .and(StringPredicate.hasALengthBetween(15).and(50))).otherwiseReport(passwordRuleFailMessage);

    Assertions.assertThat(Verifications.verifyThat(goodPassword)
                    .compliesWith(passwordRule)
                    .validate())
            .isTrue();
  }

  @Test
  void orElseThrow_goodPassword_shouldNotThrowException() {
    Assertions.assertThatNoException().isThrownBy(
            () -> Verifications.verifyThat(goodPassword)
                    .compliesWith(passwordRule)
                    .orElseThrow(RuntimeException::new));
  }

  @Test
  void validate_badPassword_shouldReturnFalse() {
    Assertions.assertThat(Verifications.verifyThat(badPassword)
            .compliesWith(passwordRule)
            .validate()).isFalse();
  }

  @Test
  void orElseThrow_badPassword_shouldThrowException() {
    var validation = Verifications.verifyThat(badPassword)
            .compliesWith(passwordRule);
    Assertions.assertThatThrownBy(() -> validation.orElseThrow(RuntimeException::new))
            .hasMessageContaining(passwordRuleFailMessage);
  }

  @Test
  void orElseReturn_badPassword_shouldReturnFailMessage() {
    Assertions.assertThat(Verifications.verifyThat(badPassword)
                    .compliesWith(passwordRule)
                    .orElseReturn(message -> message))
            .contains(passwordRuleFailMessage);
  }

  @Test
  void useCase_alphabeticInputValidation_shouldNotThrowException() {
    var alphabeticStringRule1 = Definitions.defineThatIt(StringPredicate.isAlphabetic()).otherwiseReport("It's not alphabetic");
    var alphabeticStringRule2 = Definitions.defineThatIt(StringPredicate.isNotNumeric()).otherwiseReport("It's not alphabetic");
    var alphabeticStringRule3 = Definitions.defineThatIt(StringPredicate.isAlphabetic().and(StringPredicate.isNotNumeric())).otherwiseReport("It's not alphabetic");
    Assertions.assertThatNoException().isThrownBy(
            () -> Verifications.verifyThat("test").compliesWith(alphabeticStringRule1)
                    .and(alphabeticStringRule2)
                    .and(alphabeticStringRule3)
                    .andThen(super::interact)
                    .orElseThrow(RuntimeException::new));
    Assertions.assertThat(hasBeenInteractedWith()).isTrue();
  }

  @Test
  void useCase_validateAgainstValidOtherInput_shouldNotThrowException() {
    var rule = defineThatIt(ObjectPredicate.isA(TestObject.class).where((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");
    var rule2 = defineThatIt(ObjectPredicate.isA(TestObject.class).that((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");

    Assertions.assertThatNoException().isThrownBy(() -> Verifications.verifyThat(new TestObject()).compliesWith(rule)
            .whileApplying(DEFAULT_MESSAGE)
            .and(rule2)
            .andThen(super::interact)
            .orElseThrow(RuntimeException::new));
    Assertions.assertThat(hasBeenInteractedWith()).isTrue();
  }

  @Test
  void useCase_validateAgainstValidOtherInputUsingConsumer_shouldNotThrowException() {
    var rule = defineThatIt(ObjectPredicate.isA(TestObject.class).where((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");
    var rule2 = defineThatIt(ObjectPredicate.isA(TestObject.class).that((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");
    var input = new TestObject();
    Assertions.assertThatNoException().isThrownBy(() -> Verifications.verifyThat(input).compliesWith(rule)
            .whileApplying(DEFAULT_MESSAGE)
            .and(rule2)
            .andThen(x -> {
              Assertions.assertThat(x).isEqualTo(input);
              super.interact();
            })
            .orElseThrow(RuntimeException::new));
    Assertions.assertThat(hasBeenInteractedWith()).isTrue();
  }

  @Test
  void useCase_validateAgainstValidOtherInputUsingFunction_shouldNotThrowException() {
    var rule = defineThatIt(ObjectPredicate.isA(TestObject.class).where((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");
    var rule2 = defineThatIt(ObjectPredicate.isA(TestObject.class).that((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");
    var input = new TestObject();
    Assertions.assertThatNoException().isThrownBy(
            () -> Assertions.assertThat(Verifications.verifyThat(input).compliesWith(rule)
                    .whileApplying(DEFAULT_MESSAGE)
                    .and(rule2)
                    .andThen(x -> {
                      Assertions.assertThat(x).isEqualTo(input);
                      return super.interactAndReturn();
                    })
                    .orElseThrow(RuntimeException::new)).isTrue());
    Assertions.assertThat(hasBeenInteractedWith()).isTrue();
  }

  @Test
  void useCase_validateAgainstInvalidOtherInput_shouldThrowException() {
    var rule = defineThatIt(ObjectPredicate.isA(TestObject.class).where((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");
    var rule2 = defineThatIt(ObjectPredicate.isA(TestObject.class).that((x, y) -> x.getMessage().equals(y))).otherwiseReport("failure");
    var validation = Verifications.verifyThat(new TestObject()).compliesWith(rule)
            .whileApplying("This is not what I would expect")
            .and(rule2)
            .andThen(super::interact);
    Assertions.assertThatThrownBy(() -> validation.orElseThrow(RuntimeException::new))
            .isInstanceOf(RuntimeException.class);
    Assertions.assertThat(hasBeenInteractedWith()).isFalse();
  }

  @Test
  void orElseLogMessage_validInput_shouldReturnTrue() {
    var input = "test";
    var rule = defineThatIt(ObjectPredicate.isA(String.class).where(x -> x.equals(input))).otherwiseReport("failure");
    Assertions.assertThat(Verifications.verifyThat(input).compliesWith(rule).orElseLogMessage()).isTrue();
  }

  @Test
  void orElseLogMessage_invalidInput_shouldReturnFalse() {
    var input = "test";
    var rule = defineThatIt(ObjectPredicate.isA(String.class).where(x -> x.equals("something else"))).otherwiseReport("failure");
    Assertions.assertThat(Verifications.verifyThat(input).compliesWith(rule).orElseLogMessage()).isFalse();
  }
}

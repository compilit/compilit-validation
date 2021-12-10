package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.api.contracts.ValidationBuilder;

import static org.solidcoding.validation.predicates.StringPredicate.contains;
import static org.solidcoding.validation.predicates.StringPredicate.hasALengthOf;
import static testutil.TestValue.TEST_CONTENT;

class VerificationsTests {

  @Test
  void makeSure_shouldReturnNewValidatorEntryPoint() {
    Assertions.assertThat(Verifications.verifyThat(TEST_CONTENT)).isInstanceOf(ValidationBuilder.class);
  }

  @Test
  void completeRule_formattedMessage_shouldReturnFormattedMessage() {
    var ruleFailMessage = "%s does not contain 123!";
    var value = "4";
    var rule1 = Definitions.defineThatIt(contains("123")).otherwiseReport(ruleFailMessage, value);
    var rule2 = Definitions.defineThatIt(hasALengthOf(1)).otherwiseReport(ruleFailMessage, value);
    var expectedMessage = String.format(ruleFailMessage, value);
    var validator = Verifications.verifyThat(value).compliesWith(rule1).and(rule2);
    validator.validate();
    var actual = validator.getMessage();
    Assertions.assertThat(actual).contains(expectedMessage);
  }

}

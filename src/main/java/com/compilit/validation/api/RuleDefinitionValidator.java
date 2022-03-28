package com.compilit.validation.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.compilit.validation.api.contracts.LoggingValidator;
import com.compilit.validation.api.contracts.Rule;
import com.compilit.validation.api.contracts.Validator;

import java.util.ArrayList;
import java.util.List;

abstract class RuleDefinitionValidator<T> implements Validator, LoggingValidator {

  static final String DEFAULT_MESSAGE = "Nothing to report";
  protected final List<Rule<T>> ruleDefinitions = new ArrayList<>();
  protected final List<Rule.Extended<T>> xRuleDefinitions = new ArrayList<>();
  protected final T value;
  protected final Object argument;
  protected final boolean isExtended;
  protected String message = DEFAULT_MESSAGE;

  RuleDefinitionValidator(final List<Rule<T>> ruleDefinitions, final T value) {
    this.ruleDefinitions.addAll(ruleDefinitions);
    this.value = value;
    this.argument = null;
    this.isExtended = false;
  }

  RuleDefinitionValidator(final List<Rule.Extended<T>> xRuleDefinitions,
                          final T value,
                          final Object argument) {
    this.xRuleDefinitions.addAll(xRuleDefinitions);
    this.value = value;
    this.argument = argument;
    this.isExtended = true;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public boolean validate() {
    final var stringBuilder = new StringBuilder();
    var isValid = true;
    if (isExtended) {
      isValid = validateExtendedRules(stringBuilder);
    } else {
      isValid = validateRules(stringBuilder);
    }
    if (!isValid) {
      message = stringBuilder.toString();
    }
    return isValid;
  }

  private boolean validateRules(StringBuilder stringBuilder) {
    var isValid = true;
    for (final var ruleDefinition : ruleDefinitions) {
      if (!ruleDefinition.test(value)) {
        stringBuilder.append("Broken rule: ").append(ruleDefinition.getMessage()).append("\n");
        isValid = false;
      }
    }
    return isValid;
  }

  private boolean validateExtendedRules(StringBuilder stringBuilder) {
    var isValid = true;
    for (final var biRuleDefinition : xRuleDefinitions) {
      if (!biRuleDefinition.test(value, argument)) {
        stringBuilder.append("Broken rule: ")
                .append(biRuleDefinition.getMessage())
                .append("\n");
        isValid = false;
      }
    }
    return isValid;
  }

  @Override
  public boolean orElseLogMessage() {
    var logger = LoggerFactory.getLogger(Verifications.class);
    return orElseLogMessage(logger);
  }

  @Override
  public boolean orElseLogMessage(final Logger logger) {
    final var isValid = validate();
    if (!isValid) {
      logger.error(getMessage());
    }
    return isValid;
  }

}

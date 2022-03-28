package com.compilit.validation.api;

import com.compilit.validation.api.contracts.Rule;
import com.compilit.validation.predicates.ObjectPredicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import testutil.TestObject;

class DefinitionsTests {

  @Test
  void thatIt_shouldReturnCorrectBuilder() {
    var rule1 = Definitions.defineThatIt((x) -> true).otherwiseReport("fail");
    Assertions.assertThat(rule1).isInstanceOf(Rule.class);
    var rule2 = Definitions.defineThatIt(ObjectPredicate.isA(TestObject.class).where((x) -> true)).otherwiseReport("fail");
    Assertions.assertThat(rule2).isInstanceOf(Rule.class);
    var ruleExtended1 = Definitions.defineThatIt((x, y) -> true).otherwiseReport("fail");
    Assertions.assertThat(ruleExtended1).isInstanceOf(Rule.Extended.class);
    var ruleExtended2 = Definitions.defineThatIt(ObjectPredicate.isA(TestObject.class).where((x, y) -> x.getMessage().equals(y))).otherwiseReport("fail");
    Assertions.assertThat(ruleExtended2).isInstanceOf(Rule.Extended.class);
  }
}

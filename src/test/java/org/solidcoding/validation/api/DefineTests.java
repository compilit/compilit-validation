package org.solidcoding.validation.api;

import static org.solidcoding.validation.predicates.ObjectPredicateRuleBuilder.isA;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import testutil.TestObject;


class DefineTests {

    @Test
    void thatIt_shouldReturnCorrectBuilder() {
        var rule1 = Define.thatIt((x) -> true).otherWiseReport("fail");
        Assertions.assertThat(rule1).isInstanceOf(Rule.class);
        var rule2 = Define.thatIt(isA(TestObject.class).where((x) -> true)).otherWiseReport("fail");
        Assertions.assertThat(rule2).isInstanceOf(Rule.class);
        var ruleExtended1 = Define.thatIt((x, y) -> true).otherWiseReport("fail");
        Assertions.assertThat(ruleExtended1).isInstanceOf(Rule.Extended.class);
        var ruleExtended2 = Define.thatIt(isA(TestObject.class).where((x, y) -> x.getMessage().equals(y))).otherWiseReport("fail");
        Assertions.assertThat(ruleExtended2).isInstanceOf(Rule.Extended.class);
    }
}

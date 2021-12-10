package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import testutil.TestObject;

import static org.solidcoding.validation.predicates.ObjectPredicate.isA;

class DefineTests {

    @Test
    void thatIt_shouldReturnCorrectBuilder() {
        var rule1 = Define.thatIt((x) -> true).otherwiseReport("fail");
        Assertions.assertThat(rule1).isInstanceOf(Rule.class);
        var rule2 = Define.thatIt(isA(TestObject.class).where((x) -> true)).otherwiseReport("fail");
        Assertions.assertThat(rule2).isInstanceOf(Rule.class);
        var ruleExtended1 = Define.thatIt((x, y) -> true).otherwiseReport("fail");
        Assertions.assertThat(ruleExtended1).isInstanceOf(Rule.Extended.class);
        var ruleExtended2 = Define.thatIt(isA(TestObject.class).where((x, y) -> x.getMessage().equals(y))).otherwiseReport("fail");
        Assertions.assertThat(ruleExtended2).isInstanceOf(Rule.Extended.class);
    }
}

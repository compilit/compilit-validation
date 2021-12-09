package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.newapi.Define;

class DefineTests {

    @Test
    void thatIt_shouldReturnRuleBuilder() {
        var actual = Define.thatIt(x -> true);
        Assertions.assertThat(actual).isInstanceOf(RuleBuilder.class);
    }

}

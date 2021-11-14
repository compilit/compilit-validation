package org.solidcoding.validation;

import static org.solidcoding.validation.ObjectPredicate.beA;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ObjectPredicateTests {

  @Test
  void beA_String_shouldReturnObjectPredicate() {
    var actual = DefineThat.itShould(beA(String.class).that(x -> x.contains("test")));
    Assertions.assertThat(Validator.makeSure("test").compliesWith(actual).validate()).isTrue();
  }
}

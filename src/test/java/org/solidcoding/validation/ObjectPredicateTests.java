package org.solidcoding.validation;

import static org.solidcoding.validation.ObjectPredicate.beA;
import static org.solidcoding.validation.ObjectPredicate.beAn;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ObjectPredicateTests {

  @Test
  void beA_String_shouldReturnObjectPredicate() {
    var rule = DefineThat.itShould(beA(String.class).that(x -> x.contains("test")));
    Assertions.assertThat(Validator.makeSure("test").compliesWith(rule).validate()).isTrue();
  }

  @Test
  void beAn_Object_shouldReturnObjectPredicate() {
    var actualObject = new Object();
    var rule = DefineThat.itShould(beAn(Object.class).that(x -> x.equals(actualObject)));
    Assertions.assertThat(Validator.makeSure(actualObject).compliesWith(rule).validate()).isTrue();
  }
}

package com.compilit.validation.predicates;

import com.compilit.validation.api.Definitions;
import com.compilit.validation.api.Verifications;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import testutil.TestObject;

import static com.compilit.validation.predicates.StringPredicate.isNotNull;


class ObjectPredicateTests {

  @Test
  void beA_String_shouldReturnObjectPredicate() {
    var rule = Definitions.defineThatIt(ObjectPredicate.isA(String.class).where(x -> x.contains("test"))).otherwiseReport("failure");
    Assertions.assertThat(Verifications.verifyThat("test").compliesWith(rule).validate()).isTrue();
  }

  @Test
  void beAn_Object_shouldReturnObjectPredicate() {
    var actualObject = new Object();
    var rule = Definitions.defineThatIt(ObjectPredicate.isAn(Object.class).where(x -> x.equals(actualObject))).otherwiseReport("failure");
    Assertions.assertThat(Verifications.verifyThat(actualObject).compliesWith(rule).validate()).isTrue();
  }

  @Test
  void beA_TestObjectWithoutValues_shouldReturnTrue() {
    var actualObject = new TestObject();
    actualObject.add("test");
    var rule = Definitions.defineThatIt(ObjectPredicate.isA(TestObject.class).where(TestObject::hasValues)).otherwiseReport("failure");
    Assertions.assertThat(Verifications.verifyThat(actualObject).compliesWith(rule).validate()).isTrue();
  }

  @Test
  void beA_TestObjectWithValues_shouldReturnOtherValue() {
    var failureMessage = "Something went wrong";
    var actualObject = new TestObject();
    var otherObject = new TestObject();
    var rule = Definitions.defineThatIt(ObjectPredicate.isA(TestObject.class).where(TestObject::hasValues)).otherwiseReport(failureMessage);
    var rule2 = Definitions.defineThatIt(ObjectPredicate.isA(TestObject.class).where(TestObject::hasValues)).otherwiseReport(failureMessage);
    Assertions.assertThat(Verifications.verifyThat(actualObject).compliesWith(rule).and(rule2).orElseReturn(otherObject)).isEqualTo(otherObject);
    Assertions.assertThat(Verifications.verifyThat(actualObject).compliesWith(rule).and(rule2).orElseReturn(otherObject)).isNotEqualTo(actualObject);
    Assertions.assertThat(Verifications.verifyThat(actualObject).compliesWith(rule).and(rule2).orElseReturn(x -> otherObject)).isNotEqualTo(actualObject);
    Assertions.assertThat(Verifications.verifyThat(actualObject).compliesWith(rule).and(rule2).orElseReturn(TestObject::new).getMessage()).contains(failureMessage);
  }

  @Test
  void isNotNull_shouldAddNotNullPredicate() {
    var rule = Definitions.defineThatIt(StringPredicate.isNotNull()).otherwiseReport("failure");
    Assertions.assertThat(Verifications.<String>verifyThat(null).compliesWith(rule).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat("test").compliesWith(rule).validate()).isTrue();
  }
}

# solidcoding-validation

A small package that enables validation of (business) rules through a fluent API.

# Installation

Get this dependency with the latest version

```
    <dependency>
      <artifactId>solidcoding-validation</artifactId>
      <groupId>org.solidcoding</groupId>
    </dependency>
```

# Usage

The package offers a set of Predicates to define business rules that can be validated through the Validator class. The
idea is that a rule should be a separate entity that can either be part of an object or passed throughout your
application. An individual value or object can then be tested against these rules when needed. A rule is in fact an
extension of the Predicate class. This extension allows you to add a message that is associated with the failure of this
rule.

First define a rule (another Predicate containing a fail message) using the Define entrypoint for the fluent API. Here
are some examples:

```
    var rule = Define.thatIt(contains("test")).otherwiseReport("it does not contain 'test'");
    
    var rule = Define.thatIt(isEqualTo("test")).otherwiseReport("it is not equal to 'test'");

    var rule = Define.thatIt(hasALengthOf(4)).otherwiseReport("One or more rules failed");

    var rule = Define.thatIt(isA(String.class).where(it -> it.contains("test"))).otherwiseReport("I am a failure");
   
    var rule = Define.thatIt(isA(TestClass.class).where(it -> it.hasSomeProperty()).otherwiseReport("I am a failure");
    
    var rule = Define.thatIt(isA(TestObject.class).that(x -> x.isAwesome())).otherwiseReport("I am a failure"); 

    var rule = Define.thatIt(isAlphabetic()).otherwiseReport("I am a failure");

    var rule = Define.thatIt(isEqualTo(2)).otherwiseReport("I am a failure");

    var rule = Define.thatIt(isANumber().between(1).and(5)).otherwiseReport("I am a failure");

    var rule = Define.thatIt(hasAmountOfDigits(10)).otherwiseReport("I am a failure");
    
```

Then validate the rule (returning a boolean):

```
    var result = MakeSure.that("test").compliesWith(rule).validate();
```

Or define what Exception should be thrown if a rule is broken (otherwise also returning a boolean):

```
    var result = MakeSure.that("test").compliesWith(rule).orElseThrow(new RuntimeException("whoops!));
```

You can also chain multiple rules for one value:

```
    var result = MakeSure.that("test")
        .compliesWith(rule1)
        .and(rule2)
        .orElseThrow(new RuntimeException("whoops!));
```

Finally, after validating, it's also possible to chain a process by passing a supplier. Because it returns an actual
value, the orElse clause is required to finish the statement:

```
    return MakeSure.that("test")
        .compliesWith(rule)
        .andThen(() -> someAction())
        .orElseThrow(new RuntimeException("whoops!));
```

Currently, the package offers string Predicates, number Predicates, decimal number predicates and custom object
predicates. These are all convenience methods and more will likely be added in the future. But the real strength lies in
adding your own custom predicates.

### Custom Predicate<T> implementations

Just to be clear, here is an example of how one would define custom, more complex Predicates about some object.

```
var rule = Define.thatIt(hasSomeAmazingProperties());

private Predicate<CustomObject> hasSomeAmazingProperties() {
  return input -> {
    (...something something input something...)
  };
}


MakeSure.that(value)
    .compliesWith(rule)
    .andThen(() -> performThisAction())
    .orElseThrow(new Exception());
```

### When validation takes place

It should be noted that no validation will be taking place until requested by the validate() or the orElse*() methods.
Requesting the message before validation has taken place will always result in a placeholder message like "Nothing
to report". If you would like to get the message of a validation. You could do it like this:

```
    var validator = MakeSure.that(value).compliesWith(rule);
    validator.validate();
    var actual = validator.getMessage();
```


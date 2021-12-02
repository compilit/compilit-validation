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

First define a rule (another Predicate) using the DefineThat entrypoint for the fluent API:

```
    var rule = DefineThat.itShould(beAString("test")).otherwiseReport("it is not equal to 'test'"); //for strings

    var rule = DefineThat.itShould(beAString().containing("test")).otherwiseReport("it does not contain to 'test'"); //for strings

    var rule = DefineThat.itShould(beAStringWithLength(4).containing("test")).otherwiseReport("One or more rules failed"); //for strings

    var rule = DefineThat.itShould(beAString(with -> with.length() == 4)).otherwiseReport("I am a failure"); //for strings

    var rule = DefineThat.itShould(beAlphabetic()).otherwiseReport("I am a failure"); //for strings

    var rule = DefineThat.itShould(beANumber(2)).otherwiseReport("I am a failure"); //for integers

    var rule = DefineThat.itShould(beANumber().between(1).and(5)).otherwiseReport("I am a failure"); //for integers

    var rule = DefineThat.itShould(beANumberOfLength(1)).otherwiseReport("I am a failure"); //for integers

    var rule = DefineThat.itShould(beANumber(that -> that.equals(2))).otherwiseReport("I am a failure"); //for integers

    var rule = DefineThat.itShould(beANumber().containing(2)).otherwiseReport("I am a failure"); //for integers

    var rule = DefineThat.itShould(beA(String.class).that(x -> x.contains("test"))).otherwiseReport("I am a failure");  //for custom objects

    var rule = DefineThat.itShould(beA(TestObject.class).that(x -> x.isAwesome())).otherwiseReport("I am a failure");  //for custom objects
    
```

Then validate the rule (returning a boolean):

```
    var result = Validator.makeSure("test").compliesWith(rule).validate();
```

Or define what Exception should be thrown if a rule is broken (otherwise also returning a boolean):

```
    var result = Validator.makeSure("test").compliesWith(rule).orElseThrow(new RuntimeException("whoops!));
```

You can also chain multiple rules for one value:

```
    var result = Validator.makeSure("test")
        .compliesWith(rule1)
        .and(rule2)
        .orElseThrow(new RuntimeException("whoops!));
```

Finally, after validating, it's also possible to chain a process by passing a supplier. Because it returns an actual
value, the orElse clause is required to finish the statement:

```
    return Validator.makeSure("test")
        .compliesWith(rule)
        .andThen(() -> someAction())
        .orElseThrow(new RuntimeException("whoops!));
```

Currently, the package offers string Predicates, number Predicates, decimal number predicates and custom object
predicates. These are all convenience methods and more will likely be added in the future. But the real strength lies in
adding your own custom predicates.

### Custom Predicate<T> implementations

The "that", "and" and "itShould" methods invite you to implement your own Predicates in the following way:

```
var rule = DefineThat.itShould(haveSomeAmazingProperties());

private Predicate<CustomObject> haveSomeAmazingProperties() {
  return input -> {
    (...something something input something...)
  };
}


Validator.makeSure(value)
    .compliesWith(rule1)
    .andThen(() -> performThisAction())
    .orElseThrow(new Exception());
```

It should be noted that no validation will be taking place until requested by the validate() or the orElse*() methods.
Requesting the failMessage before validation has taken place will always result in a placeholder message like "Nothing
to report". If you would like to get the failMessage of a validation. You could do it like this:

```
    var validator = Validator.makeSure(value).otherwiseReport("failure");
    validator.compliesWith(rule).validate();
    var actual = validator.getMessage();
```


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
    var rule = DefineThat.it(shouldBeAString("test")).otherwiseReport("it is not equal to 'test'"); //for strings

    var rule = DefineThat.it(shouldBeAString().containing("test")).otherwiseReport("it does not contain to 'test'"); //for strings

    var rule = DefineThat.it(shouldBeAStringWithLength(4).containing("test")).otherwiseReport("One or more rules failed"); //for strings

    var rule = DefineThat.it(shouldBeAString(with -> with.length() == 4)).otherwiseReport("I am a failure"); //for strings

    var rule = DefineThat.it(shouldBeAlphabetic()).otherwiseReport("I am a failure"); //for strings

    var rule = DefineThat.it(shouldBeANumber(2)).otherwiseReport("I am a failure"); //for integers

    var rule = DefineThat.it(shouldBeANumber().between(1).and(5)).otherwiseReport("I am a failure"); //for integers

    var rule = DefineThat.it(shouldBeANumberOfLength(1)).otherwiseReport("I am a failure"); //for integers

    var rule = DefineThat.it(shouldBeANumber(that -> that.equals(2))).otherwiseReport("I am a failure"); //for integers

    var rule = DefineThat.it(shouldBeANumber().containing(2)).otherwiseReport("I am a failure"); //for integers

    var rule = DefineThat.it(shouldBeA(String.class).that(x -> x.contains("test"))).otherwiseReport("I am a failure");  //for custom objects

    var rule = DefineThat.it(shouldBeA(TestObject.class).that(x -> x.isAwesome())).otherwiseReport("I am a failure");  //for custom objects
    
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

The "that", "where", "and" and "it" methods invite you to implement your own Predicates in the following way:

```
var rule = DefineThat.it(shouldHaveSomeAmazingProperties());

private Predicate<CustomObject> shouldHaveSomeAmazingProperties() {
  return input -> {
    (...something something input something...)
  };
}


Validator.makeSure(value)
    .compliesWith(rule1)
    .andThen(() -> performThisAction())
    .orElseThrow(new Exception());
```

I've made the choice to start all predicates with the word 'should'. But obviously you can just as easily choose
different, more demanding words like 'has', 'is', 'must' etc. The reason I chose 'should' is that it is a rule about
something that is not yet presented. In the best case it 'should' have some property. In the context of a Predicate,
where the actual value is not yet present, it makes no sense to me to actually use the standard boolean conventional
words like 'has', 'is', 'must' etc. But maybe that's just me.

### When validation takes place

It should be noted that no validation will be taking place until requested by the validate() or the orElse*() methods.
Requesting the failMessage before validation has taken place will always result in a placeholder message like "Nothing
to report". If you would like to get the failMessage of a validation. You could do it like this:

```
    var validator = Validator.makeSure(value).otherwiseReport("failure");
    validator.compliesWith(rule).validate();
    var actual = validator.getMessage();
```


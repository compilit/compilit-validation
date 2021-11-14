# solidcoding-validation

A small package that enables validation of business rules through a fluent API

# Installation

Get this dependency with the latest version

```
    <dependency>
      <artifactId>solidcoding-validation</artifactId>
      <groupId>org.solidcoding</groupId>
    </dependency>
```

# Usage

The package offers a set of Predicates to define business rules that can be validated through the
Validator class.

First define a rule (another Predicate) using the DefineThat entrypoint for the fluent API:

```
    var rule = DefineThat.itShould(beAString("test")); //for String

    var rule = DefineThat.itShould(beAString("test")); //for String

    var rule = DefineThat.itShould(beAString().containing("test")); //for String

    var rule = DefineThat.itShould(beAStringWithLength(4).containing("t")); //for String

    var rule = DefineThat.itShould(beAString(with -> with.length() == 4)); //for String

    var rule = DefineThat.itShould(beAlphabetic()); //for String

    var rule = DefineThat.itShould(beANumber(2)); //for integers

    var rule = DefineThat.itShould(beANumber().between(1).and(5)); //for integers

    var rule = DefineThat.itShould(beANumberOfLength(1)); //for integers

    var rule = DefineThat.itShould(beANumber(that -> that.equals(2))); //for integers

    var rule = DefineThat.itShould(beANumber().containing(2)); //for integers

    var rule = DefineThat.itShould(beA(String.class).that(x -> x.contains("test")));  //for custom objects

    var rule = DefineThat.itShould(beA(TestObject.class).that(x -> x.hasValues()));  //for custom objects
```

Then validate the rule (returning a boolean):

```
    Validator.makeSure("test").compliesWith(rule).validate();
```

Or define what Exception should be thrown if a rule is broken:

```
    Validator.makeSure("test").compliesWith(rule).orElseThrow(new RuntimeException("whoops!));
```

You can also chain multiple rules for one value:

```
    Validator.makeSure("test")
        .compliesWith(rule1)
        .compliesWith(rule2)
        .orElseThrow(new RuntimeException("whoops!));
```

Currently, the package offers String Predicates and Integer Predicates. More will be added in the
future.
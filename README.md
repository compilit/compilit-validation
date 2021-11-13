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

The package offers a set of Predicates to define business rules that can be validated through the Validator class.

First define a rule using the DefineThat entrypoint for the fluent API:
```
    var rule = DefineThat.itShould(beAString("test"));
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

Currently, the package offers String Predicates and Integer Predicates. More will be added in the future.
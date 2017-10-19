# lenientfun

[![Apache License 2.0](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/mictaege/lenientfun.svg?branch=develop)](https://travis-ci.org/mictaege/lenientfun)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=com.github.mictaege.lenientfun%3Adevelop)](https://sonarcloud.io/dashboard/index/com.github.mictaege.lenientfun%3Adevelop)

In order to delegate the burden of exception handling from an API user to an API provider, this is a rewritten version of the Java functional interfaces with checked exceptions signatures.

In Java 8 lambda expressions and method references has been introduced in order to facilitate a functional programming style. This way a lot of problems could be solved in a far more short and concise way. But problems arise if exception handling comes into play and readability is quickly lost. The main reason for this is that Javas functional interfaces - such as _Function_, _Consumer_, _Supplier_ etc. - does not declare any exception in their signatures, which leads to a situation that 
- all checked exceptions has to be handled inside the lambda expression
- no method reference could be used for a method that declares a checked exception

## Problem

Assumed that we have a _PersonConverter_ that is able to convert _Persons_ into any _String_ using a given _Function_:

```Java
public String convert(final Person person, final Function<Person, String> converter) {
    return converter.apply(person);
}
``` 

Now we are able to convert _Persons_ into any format we want to:

```Java
final String json = converter.convert(person, p -> "{\n" +
        "  \"firstName\": \"" + p.getFirstName() + "\",\n" +
        "  \"surName\": \"" + p.getSurName() + "\"\n" +
        "}");

final String yaml = converter.convert(person, p ->
        "firstName: " + p.getFirstName() + "\n" +
        "surName: " + p.getSurName() + "");
```

That's cool, isn't it?

But things are different if exception handling comes into play. If for example our converted formats should also contain a _birthday_ and the birthdays accessor declares an checked exception our code may look like follows:

```Java
final String json = converter.convert(person, p -> {
    try {
        return "{\n" +
            "  \"firstName\": \"" + p.getFirstName() + "\",\n" +
            "  \"surName\": \"" + p.getSurName() + "\",\n" +
            "  \"birthDay\": \"" + p.formatedBirthday() + "\"\n" + // throws checked exception
        "}";
    } catch (final Exception e) {
        logger.error(e);
        throw new IllegalArgumentException("Conversion fails", e);
    }
});

final String yaml = converter.convert(person, p -> {
    try {
        return "firstName: " + p.getFirstName() + "\n" +
            "surName: " + p.getSurName() + "\n" +
            "birthDay: " + p.formatedBirthday() + ""; // throws checked exception
    } catch (final Exception e) {
        logger.error(e);
        throw new IllegalArgumentException("Conversion fails", e);
    }
});
```
   
There are several problems with this code. First of all the exception handling has to be done inside the lambda expression and this makes the code less readable. Furthermore the lambda expressions seems not to be the right places to handle the exceptions at all. Wouldn't it be more reasonable if the _PersonConverter_ alone is responsible for all the exception handling stuff like logging etc.?

## Solution

With _lenientfun_ the problems described above could be avoided.

First we rewrite the _PersonConverter_ and change the Java _Function_ into a _LenientFunction_ which is aware of checked exceptions:

```Java
public String convert(final Person person, final LenientFunction<Person, String> converter) {
    try {
        return converter.apply(person);
    } catch (final Exception e) {
        logger.error(e);
        throw new IllegalArgumentException("Conversion fails", e);
    }
}
```

Now the _PersonConverter_ is responsible for all the exception handling stuff and we got rid of the exception handling inside the lambda expression:  

```Java
final String json = converter.convert(person, p -> "{\n" +
    "  \"firstName\": \"" + p.getFirstName() + "\",\n" +
    "  \"surName\": \"" + p.getSurName() + "\",\n" +
    "  \"birthDay\": \"" + p.formatedBirthday() + "\"\n" + // throws checked exception
    "}");

final String yaml = converter.convert(person, p ->
    "firstName: " + p.getFirstName() + "\n" +
    "surName: " + p.getSurName() + "\n" +
    "birthDay: " + p.formatedBirthday() + ""); // throws checked exception 
```

## Conclusion

- For every functional interface of the _java.util.function_ package _lenientfun_ provides a "lenient" version which is aware of checked exceptions.

- This "lenient" functional interfaces could be used to design own API's

- For API's that are using the Java functional interfaces there is a _LenientAdapter_ that could be used to adapt the "lenient" style:
```Java
final String json = converter.convert(person, LenientAdapter.func(p -> "{\n" + // converter expects java.util.function.Function
    "  \"firstName\": \"" + p.getFirstName() + "\",\n" +
    "  \"surName\": \"" + p.getSurName() + "\",\n" +
    "  \"birthDay\": \"" + p.formatedBirthday() + "\"\n" + // throws checked exception
    "}"));
```
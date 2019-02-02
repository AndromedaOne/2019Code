# 2019Code
2019 Robot Codebase

# After merging master onto your branch, you *must* run `gradlew spotlessApply` to allow for your branch to build.

# Configuration Documentation
//TODO: Chris or Dana fill this out

## Overall Format
The configuration this year is quite similar to JSON, with a few major differences

- The equals sign (=) can be used instead of colons
- Commas are not required
- Keys and values do not need to be in quotations unless its a string

## Example
### robot.conf
```HOCON
robot.name = Paul

foo {
    bar="foobar"
}
```
### Accessing it
```java
Config conf = ConfigFactory.parseFile(new File("/home/lvuser/robot.conf"));

String foo = conf.getString("foo.bar");
System.out.println(foo);
```
This should output `foobar`
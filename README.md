# Log4j Patch
[![Release](https://jitpack.io/v/org.glavo/log4j-patch.svg)](https://jitpack.io/#org.glavo/log4j-patch)
[![](http://www.wtfpl.net/wp-content/uploads/2012/12/wtfpl-badge-1.png)](http://www.wtfpl.net/)

Resolve the RCE vulnerability caused by JNDI lookup in log4j 2.0~2.14.1. It is licensed under the [WTFPL 2.0](http://www.wtfpl.net/faq/) license,
you can do anything with it!

This is a **non-intrusive** patch that allows you to block this vulnerability without modifying the program code/updating the dependent.
So you can use it to patch third-party programs, such as Minecraft.

The principle of the library is simple: 
It provides an empty `JndiLookup` to override the implementation in log4j. 
Log4j2 can handle this situation and safely disable JNDI lookup.

It is compatible with all versions of log4j2 (2.0~2.15).

## Usage

You can add it to the classpath by yourself, or you can use javaagent to inject it automatically.

### Use Java Agent

First, download the agent jar: [log4j-patch-agent-1.0.jar](https://github.com/Glavo/log4j-patch/releases/download/1.0/log4j-patch-agent-1.0.jar).

You only need to add the `-javaagent:log4j-patch-agent-1.0.jar` to the JVM parameter, and the agent will do everything automatically.

### Manual injection

Sometimes you may not want to use Java agent, such as when you need to generate native-image. You can download it directly from GitHub release:
[log4j-patch-1.0.jar](https://github.com/Glavo/log4j-patch/releases/download/1.0/log4j-patch-1.0.jar).

All you need to do is add it to the front of the classpath.

If you are using log4j2 as a Java module, use this JVM parameter instead of adding it to the classpath: 
`--patch-module org.apache.logging.log4j.core=log4j-patch-1.0.jar`.

## Adding patch to your build

If you are using Maven/Gradle/SBT, adding it as the first dependency should solve the problem. 

It is published on Maven Central. You can add dependencies on it in this way:

Maven:
```xml
<dependency>
    <groupId>org.glavo</groupId>
    <artifactId>log4j-patch</artifactId>
    <version>1.0</version>
</dependency>
```

Gradle:
```groovy
dependencies {
    implementation 'org.glavo:log4j-patch:1.0'
}
```

Gradle Kotlin DSL:
```kotlin
dependencies {
    implementation("org.glavo:log4j-patch:1.0")
}
```

## Check whether the replacement is successful

When JNDI lookup is disabled, log4j may print similar content in the log:
```
2021-12-10 15:50:39,521 main WARN JNDI lookup class is not available because this JRE does not support JNDI. JNDI string lookups will not be available, continuing configuration. Ignoring java.lang.ClassCastException: class org.apache.logging.log4j.core.lookup.JndiLookup
```

in addition, if you use the agent, it will set the system property `org.glavo.log4j.patch.agent.patched` to `true` when the replacement is successful.
We can use the `jinfo` command line tool to observe the system properties of the JVM process.

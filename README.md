# Log4j Patch

Resolve the RCE vulnerability caused by JNDI lookup in log4j 2.0~2.14.1. This library is placed in the **public domain**
and you can use it at will.

The principle of the library is simple: It provides an empty `JndiLookup` to override the implementation in log4j.

All you need to do is add it to the front of the classpath to disable JNDI lookup and avoid RCE vulnerabilities. It is
compiled using java 6 and is compatible with all current Java versions.

You can download it directly from GitHub
release: [log4j-patch-1.0.jar](https://github.com/Glavo/log4j-patch/releases/download/1.0/log4j-patch-1.0.jar).

## Adding patch to your build

If you are using Maven/Gradle/SBT, adding it as the first dependency should solve the problem (To be tested). 
I have publish it to [JitPack](https://jitpack.io/) and should publish it to Maven central soon. At present, please add
JitPack repository first:

Maven:
```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Gradle:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

Gradle Kotlin DSL:
```kotlin
repositories {
    maven(url = "https://jitpack.io")
}
```


Then, add a dependency on it:

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

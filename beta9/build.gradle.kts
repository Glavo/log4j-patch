@file:Suppress("GradlePackageUpdate")

plugins {
    java
}

tasks.jar {
    archiveBaseName.set("log4j-patch-beta9")
}


repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
    implementation("org.apache.logging.log4j:log4j-core:2.0-beta9")
}
import java.time.Duration
import java.util.Properties

plugins {
    java
    signing
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "org.glavo"
version = "1.0"

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.compileJava {
    sourceCompatibility = "6"
    targetCompatibility = "6"
}

repositories {
    mavenCentral()
}

tasks.withType<GenerateModuleMetadata>().configureEach {
    enabled = false
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            version = project.version.toString()
            artifactId = project.name
            from(components["java"])

            pom {
                name.set(project.name)
                description.set("Patch for Log4j 2.0~2.14.1")
                url.set("https://github.com/Glavo/log4j-patch")
                licenses {
                    license {
                        name.set("WTFPL 2.0")
                        url.set("http://www.wtfpl.net/")
                    }
                }
                developers {
                    developer {
                        id.set("glavo")
                        name.set("Glavo")
                        email.set("zjx001202@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/Glavo/log4j-patch")
                }
            }
        }
    }
}

val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    // Read local.properties file first if it exists
    val p = Properties()
    secretPropsFile.reader().use {
        p.load(it)
    }

    p.forEach { (name, value) ->
        ext[name.toString()] = value
    }
} else {
    // Use system environment variables
    ext["sonatypeUsername"] = System.getenv("OSSRH_USERNAME")
    ext["sonatypePassword"] = System.getenv("OSSRH_PASSWORD")
    ext["sonatypeStagingProfileId"] = System.getenv("SONATYPE_STAGING_PROFILE_ID")
    ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    ext["signing.key"] = System.getenv("SIGNING_KEY")
}

signing {
    useInMemoryPgpKeys(
        rootProject.ext["signing.keyId"].toString(),
        rootProject.ext["signing.key"].toString(),
        rootProject.ext["signing.password"].toString(),
    )
    sign(publishing.publications["maven"])
}

nexusPublishing {
    connectTimeout.set(Duration.ofMinutes(10))
    clientTimeout.set(Duration.ofMinutes(10))
    repositories {
        sonatype {
            stagingProfileId.set(rootProject.ext["sonatypeStagingProfileId"].toString())
            username.set(rootProject.ext["sonatypeUsername"].toString())
            password.set(rootProject.ext["sonatypePassword"].toString())
        }
    }
}

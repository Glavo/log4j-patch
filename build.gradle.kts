plugins {
    java
    `maven-publish`
}

group = "org.glavo"
version = "1.0"

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
                developers {
                    developer {
                        id.set("glavo")
                        name.set("Glavo")
                        email.set("zjx001202@gmail.com")
                    }
                }
            }
        }
    }
}
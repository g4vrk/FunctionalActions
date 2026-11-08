import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.named
import util.VersionUtility

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
}


group = System.getenv("GROUP") ?: "com.g4vrk"

val baseVersion = "2.1.2"
version = System.getenv("VERSION") ?: VersionUtility.version(project, baseVersion)

description = "Configurable action framework for Bukkit/Paper plugins."


repositories {

    mavenCentral()

    maven {

        url = uri("https://repo.papermc.io/repository/maven-public/")

    }

}


dependencies {

    compileOnly(libs.paper.api)
    testImplementation(libs.paper.api)
    testImplementation("org.junit.jupiter:junit-jupiter:6.1.3")
    testImplementation("net.kyori:adventure-api:4.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.1.3")

}


java {

    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    withSourcesJar()

}


tasks {

    withType<JavaCompile>().configureEach {

        options.encoding = "UTF-8"

    }

    shadowJar {

        archiveFileName = "${rootProject.name}-${rootProject.version}.jar"

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        mergeServiceFiles()

    }

    jar {

        enabled = false

    }

    build {

        dependsOn(shadowJar)

    }

}

defaultTasks("clean", "build")

publishing {

    publications {

        create<MavenPublication>("maven") {

            groupId = System.getenv("GROUP") ?: project.group.toString()
            artifactId = System.getenv("ARTIFACT") ?: rootProject.name
            version = System.getenv("VERSION") ?: project.version.toString()

            artifact(tasks.named<ShadowJar>("shadowJar")) {
                classifier = ""
            }
            artifact(tasks.named<Jar>("sourcesJar"))

            pom {
                name.set(rootProject.name)
                description.set(project.description)
            }

        }

    }

}

tasks.test { useJUnitPlatform() }

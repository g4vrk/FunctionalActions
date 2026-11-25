import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.named
import util.VersionUtility

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
}


group = "com.g4vrk"

val baseVersion = "2.2.2"
version = VersionUtility.version(project, baseVersion)

description = "Configurable action framework for Bukkit/Paper plugins."


repositories {

    mavenCentral()

    maven {

        url = uri("https://repo.papermc.io/repository/maven-public/")

    }

}


dependencies {

    compileOnly(libs.paper.api)
    implementation(libs.xseries)

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("net.kyori:adventure-api:4.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.4")

}


java {

    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16

    withSourcesJar()

}

tasks.named<JavaCompile>("compileJava") {

    options.release.set(16)

}


tasks {

    withType<JavaCompile>().configureEach {

        options.encoding = "UTF-8"

    }

    withType<Test>().configureEach {

        useJUnitPlatform()

    }

    shadowJar {

        archiveFileName = "${rootProject.name}-${rootProject.version}.jar"

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        relocate("com.cryptomorin.xseries", "com.g4vrk.functionalActions.internal.xseries")

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

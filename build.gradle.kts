import util.VersionUtility

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
}


group = "com.g4vrk"

val baseVersion = "2.1.0"
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

            artifact(tasks.named("shadowJar"))
            artifact(tasks.named("sourcesJar"))

            pom {
                name.set(rootProject.name)
                description.set(project.description)
            }

        }

    }

}

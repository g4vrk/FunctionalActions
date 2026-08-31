import util.VersionUtility

plugins {
    `java-library`
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
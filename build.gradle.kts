plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

val targetJavaVersion = 8
allprojects {
    group = "top.mrxiaom"
    version = "1.0.2"

    apply(plugin = "java")
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://repo.rosewooddev.io/repository/public/")
        maven("https://jitpack.io")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    java {
        val javaVersion = JavaVersion.toVersion(targetJavaVersion)
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        if (JavaVersion.current() < javaVersion) {
            toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
        }
    }

    tasks {
        withType<JavaCompile>().configureEach {
            if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
                options.release.set(targetJavaVersion)
            }
        }
    }

}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20-R0.1-SNAPSHOT")
    implementation("org.jetbrains:annotations:24.0.0")
    implementation(project(":nms"))
}

tasks {
    jar {
        enabled = false
    }
    shadowJar {
        archiveClassifier.set("")
        destinationDirectory.set(rootProject.file("out"))
    }
    build {
        dependsOn(shadowJar)
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        from(sourceSets.main.get().resources.srcDirs) {
            expand("version" to project.version)
            include("plugin.yml")
        }
    }
}

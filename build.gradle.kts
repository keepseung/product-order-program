plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

group = "kr.co._29cm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.9.2")
    testImplementation("org.assertj", "assertj-core", "3.24.2")
    testImplementation("io.kotest", "kotest-runner-junit5", "5.2.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

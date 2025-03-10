plugins {
    id("java")
}

group = "asmd.lab3"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // JUnit 5 dependencies
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")

    // Test runtime dependency for JUnit Platform Launcher
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.9.0")
}

tasks.test {
    useJUnitPlatform()  // Ensure JUnit 5 is used
}

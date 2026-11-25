plugins {
    java
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named("test") {
    this as org.gradle.api.tasks.testing.Test
    useJUnitPlatform()
}

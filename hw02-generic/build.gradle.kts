plugins {
    java
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(libs.guava)
    implementation(libs.lombok)
    annotationProcessor(libs.lombok)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile>().configureEach {
    options.annotationProcessorPath = configurations.getByName("annotationProcessor")
}

tasks.named("test") {
    this as org.gradle.api.tasks.testing.Test
    useJUnitPlatform()
}



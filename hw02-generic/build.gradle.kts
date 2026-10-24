plugins {
    application
    alias(libs.plugins.shadow)
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

application {
    mainClass = "ru.otus.home.work.Main"
}
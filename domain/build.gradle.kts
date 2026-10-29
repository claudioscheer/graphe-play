plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.junit)
}

tasks.named<Test>("test") {
    inputs.dir(rootProject.file("content/pt"))
    inputs.file(rootProject.file("content/version.txt"))
}

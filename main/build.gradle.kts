plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.6.20-1.0.5"
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.11.0")
    ksp(project(":ksp"))
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}
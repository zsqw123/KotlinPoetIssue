plugins {
    kotlin("jvm")
    id("kotlin-kapt")
}

dependencies {
    val poetVersion = "1.11.0"
    kapt("com.google.auto.service:auto-service:1.0.1")
    implementation("com.google.auto.service:auto-service:1.0.1")
    implementation("com.squareup:kotlinpoet:$poetVersion")
    implementation("com.squareup:kotlinpoet-ksp:$poetVersion")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.6.20-1.0.5")
}

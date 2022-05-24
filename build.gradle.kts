import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    kotlin("jvm") version libs.versions.kotlin.get()
}

group = "com.mxalbert.compose"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
}

tasks.test {
    useJUnitPlatform()
}

gradlePlugin {
    plugins {
        create("nestedScrollFixPlugin") {
            id = "com.mxalbert.compose.nestedscrollfix"
            implementationClass = "com.mxalbert.compose.NestedScrollFixPlugin"
        }
    }
}

dependencies {
    implementation(libs.agp.api)
    implementation(libs.bundles.asm)

    testImplementation(kotlin("test"))
}

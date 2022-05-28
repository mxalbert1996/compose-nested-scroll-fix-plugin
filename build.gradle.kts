import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin)
    alias(libs.plugins.maven.publish)
}

group = "com.mxalbert.compose"
version = property("VERSION_NAME") as String

repositories {
    google()
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
}

tasks.test {
    useJUnitPlatform()
    dependsOn(tasks.getByName("publishAllPublicationsToMavenRepository"))
    systemProperty("pluginVersion", version)
    testLogging.showStandardStreams = true
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
    compileOnly(libs.agp.api)
    compileOnly(libs.bundles.asm)

    testImplementation(libs.testParameterInjector)
}

publishing {
    repositories {
        maven(url = "$buildDir/localMaven")
    }
}

mavenPublish {
    sonatypeHost = SonatypeHost.S01
}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
}

group = "org.mvnsearch"
version = "1.0.0-SNAPSHOT"

val kotlinVersion = "1.4.10"
val ktorVersion = "1.4.1"
val rsocketKotlinVersion = "0.10.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven(url = "https://oss.jfrog.org/oss-snapshot-local")
    maven(url = "https://dl.bintray.com/kotlinx/kotlinx")
}

dependencies {
    implementation("io.rsocket.kotlin:rsocket-core:${rsocketKotlinVersion}")
    // rsocket kotlin client
    implementation("io.ktor:ktor-client-cio:${ktorVersion}")
    implementation("io.rsocket.kotlin:rsocket-transport-ktor-client:${rsocketKotlinVersion}")
    // rsocket kotlin server
    implementation("io.ktor:ktor-server-cio:${ktorVersion}")
    implementation("io.rsocket.kotlin:rsocket-transport-ktor-server:${rsocketKotlinVersion}")
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}
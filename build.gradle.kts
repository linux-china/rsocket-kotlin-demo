import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.mvnsearch"
version = "1.0.0-SNAPSHOT"

val kotlinVersion = "1.7.10"
val ktorVersion = "2.1.0"
val rsocketKotlinVersion = "0.15.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.rsocket.kotlin:rsocket-core:${rsocketKotlinVersion}")
    implementation("io.rsocket.kotlin:rsocket-ktor-client:${rsocketKotlinVersion}")
    implementation("io.rsocket.kotlin:rsocket-ktor-server:${rsocketKotlinVersion}")
    // rsocket kotlin client
    implementation("io.rsocket.kotlin:rsocket-transport-ktor-tcp:${rsocketKotlinVersion}")
    implementation("io.rsocket.kotlin:rsocket-transport-ktor-websocket:${rsocketKotlinVersion}")
    // rsocket kotlin server
    implementation("io.rsocket.kotlin:rsocket-transport-ktor-websocket-client:${rsocketKotlinVersion}")
    implementation("io.rsocket.kotlin:rsocket-transport-ktor-websocket-server:${rsocketKotlinVersion}")
    // ktor CIO
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.ktor:ktor-server-cio-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-client-cio-jvm:${ktorVersion}")
    testImplementation(kotlin("test-junit5"))
    testImplementation(platform("org.junit:junit-bom:5.9.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}


tasks.withType<KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}
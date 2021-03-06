import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
}

group = "org.mvnsearch"
version = "1.0.0-SNAPSHOT"

val kotlinVersion = "1.4.21"
val ktorVersion = "1.4.3"
val rsocketKotlinVersion = "0.12.0"

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
    implementation("ch.qos.logback:logback-classic:1.2.3")
    testImplementation(kotlin("test-junit5"))
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
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
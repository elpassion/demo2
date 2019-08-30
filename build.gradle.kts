import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "pl.elpassion"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

plugins {
    val kotlinVersion = "1.3.50"

    id("org.springframework.boot") version "2.1.7.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")
}

dependencies {
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("org.hsqldb:hsqldb")
    testImplementation("org.springframework.boot:spring-boot-starter-test") { exclude(module = "junit") }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
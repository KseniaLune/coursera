plugins {
    id("java")
    id("org.springframework.boot") version "3.1.2"
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
}

group = "com.kitten"
version = "0.0.1-SNAPSHOT"


java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.spring.starter.data.jdbc)
    implementation(libs.spring.starter.data.jpa)
    implementation(libs.slf4j)
    implementation(libs.spring.starter.data.redis)

    implementation(libs.spring.starter.validation)
    implementation(libs.spring.starter.security)
    implementation(libs.spring.starter.web)
    implementation(libs.flyway.core)
    runtimeOnly(libs.postgresql)
    implementation(libs.datasource.proxy.spring.boot.starter)

    implementation(libs.jwt.api)
    runtimeOnly(libs.bundles.jwt)

    implementation(libs.minio.impl)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.security.test)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

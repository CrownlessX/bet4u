plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.spring") version "1.9.25"
  id("org.springframework.boot") version "3.3.4"
  id("io.spring.dependency-management") version "1.1.6"
  kotlin("plugin.jpa") version "1.9.25"
}

group = "com.epam"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}

repositories {
  mavenCentral()
}

extra["springCloudGcpVersion"] = "5.7.0"
extra["springCloudVersion"] = "2023.0.3"

dependencies {
  implementation("com.google.flogger:flogger:0.8")
  implementation("com.google.flogger:flogger-slf4j-backend:0.8")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.google.cloud:spring-cloud-gcp-starter")
  implementation("com.google.code.gson:gson")

  developmentOnly("org.springframework.boot:spring-boot-devtools")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("com.google.truth:truth:1.4.4")
  testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
  testImplementation("org.mockito:mockito-core:4.11.0")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
  imports {
    mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:${property("springCloudGcpVersion")}")
    mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
  }
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

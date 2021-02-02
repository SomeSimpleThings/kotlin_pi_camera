plugins {
    kotlin("jvm") version "1.4.21"
    id("com.github.ben-manes.versions") version "0.36.0"
}

repositories {
    mavenCentral()
}

subprojects {

    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    group = "com.papuguys"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }

    val implementation by configurations

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

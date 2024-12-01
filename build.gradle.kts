import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins { kotlin("jvm") version "2.0.0" }

group = "org.example"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
  testImplementation(kotlin("test"))
  testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
}

tasks.test { useJUnitPlatform() }

val compileKotlin: KotlinCompile by tasks.withType<KotlinJvmCompile>()

compileKotlin.compilerOptions { freeCompilerArgs.add("-Xdebug") }

kotlin { jvmToolchain(17) }

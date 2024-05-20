import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	`build-dashboard`
	id("com.dorongold.task-tree") version "3.0.0"
	id("org.owasp.dependencycheck") version "9.2.0"
	id("com.github.ben-manes.versions") version "0.51.0"
	id("io.gitlab.arturbosch.detekt") version("1.23.6")
	jacoco

	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.10"
	kotlin("plugin.spring") version "1.9.10"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.6.21" apply false
}

subprojects {
	apply {
		plugin("kotlin")
		plugin("org.jetbrains.kotlin.jvm")
		plugin("io.gitlab.arturbosch.detekt")
		plugin("io.spring.dependency-management")
	}

	group = "com.stringconcat"
	version = "0.0.1"

	repositories {
		mavenCentral()
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.5")
		}
	}

	java {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = "17"
		}
	}

	detekt {
		repositories {
			mavenCentral()
		}

		toolVersion = "1.23.3"
		config.setFrom(file("${rootProject.projectDir}/tools/detekt/detekt-config.yml"))
		buildUponDefaultConfig = true

		source.from("src/main/kotlin", "src/test/kotlin", "src/test/gatling")

		dependencies {
			detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.6")
		}
	}

	tasks.withType<Detekt>().configureEach {
		reports {
			html.required.set(true)
		}
	}
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
	finalizedBy(tasks.jacocoTestCoverageVerification)
}
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
	id("org.jetbrains.kotlin.plugin.jpa") version "1.9.10" apply false
}

//apply(plugin = "io.spring.dependency-management")

allprojects {
	group = "com.stringconcat"
	version = "0.0.1"

	repositories {
		mavenCentral()
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
	}

//	tasks.named("compileKotlin", org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask::class.java) {
//		compilerOptions {
//			freeCompilerArgs.add("-Xjsr305=strict")
//		}
//	}

	apply(plugin = "kotlin")
	apply(plugin = "io.gitlab.arturbosch.detekt")


//	apply {
//		plugin("org.springframework.boot")
//		plugin("io.spring.dependency-management")
//	}

//	apply(plugin = "java")
//	apply(plugin = "kotlin")
//	apply(plugin = "io.spring.dependency-management")

//	apply {
//		plugin("io.gitlab.arturbosch.detekt")
//	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
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

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17


dependencies {
	// spring modules
	implementation("org.springframework.boot:spring-boot-starter-webflux:2.7.2")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.2")
	implementation("org.springframework.boot:spring-boot-starter-data-rest:2.7.2")

	// kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation(project(":presentation"))
	implementation(project(":persistence"))
	implementation(project(":useCasePeople"))
	implementation(project(":businessPeople"))
	implementation(project(":quoteGarden"))
	implementation(project(":avatarsDicebear"))

	// dev tools
	developmentOnly("org.springframework.boot:spring-boot-devtools:2.7.2")

	//persistance
	implementation("org.postgresql:postgresql:42.3.4")
	implementation("org.liquibase:liquibase-core:4.9.1")

	// tests
//	testCompile("org.junit.jupiter:junit-jupiter-api:5.8.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.2") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.projectreactor:reactor-test")
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
	finalizedBy(tasks.jacocoTestCoverageVerification)
}
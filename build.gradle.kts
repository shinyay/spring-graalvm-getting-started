import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	id("org.springframework.boot") version "2.4.0-M3"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.4.0"
	kotlin("plugin.spring") version "1.4.0"
}

group = "com.google.shinyay"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.experimental:spring-graalvm-native:0.8.2-SNAPSHOT")
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(group = "org.apache.tomcat.embed", module = "tomcat-embed-core")
		exclude(group = "org.apache.tomcat.embed", module = "tomcat-embed-websocket")
	}
	implementation("org.apache.tomcat.experimental:tomcat-embed-programmatic:${dependencyManagement.importedProperties["tomcat.version"]}")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
	builder = "paketobuildpacks/builder:tiny"
	environment = mapOf(
			"BP_BOOT_NATIVE_IMAGE" to "1",
			"BP_BOOT_NATIVE_IMAGE_BUILD_ARGUMENTS" to """
                -Dspring.spel.ignore=true
                -Dspring.native.remove-yaml-support=true
            """.trimIndent()
	)
	imageName = "shinyay/hello:graalvm-lightweight-tomcat"
}

//tasks.getByName<BootBuildImage>("bootBuildImage") {
//	imageName = "shinyay/hello:liberica"
//}
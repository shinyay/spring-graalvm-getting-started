# Spring GraalVM Native Image

Build GraalVM Native Image by Spring Feature

- [Spring GraalVM Native 0.8.1 reference guide](https://repo.spring.io/milestone/org/springframework/experimental/spring-graalvm-native-docs/0.8.1/spring-graalvm-native-docs-0.8.1.zip!/reference/index.html)

## Description
### 1. GraalVM
- [GraalVM Site](https://www.graalvm.org/)

### 2. Dependencies
- Spring Boot Version: `Spring Boot 2.4.0-M3`
- Artifact: `org.springframework.experimental:spring-graalvm-native:0.8.2-SNAPSHOT`
  - Repository: `https://repo.spring.io/snapshot`
  - GraalVM `20.2.0`

```kotlin
repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.experimental:spring-graalvm-native:0.8.2-SNAPSHOT")
}
```

### 3. BootBuildImage Task

```shell script
$ ./gradlew help --task bootBuildImage

> Task :help
Detailed task information for bootBuildImage

Path
     :bootBuildImage

Type
     BootBuildImage (org.springframework.boot.gradle.tasks.bundling.BootBuildImage)

Options
     --builder     The name of the builder image to use

     --imageName     The name of the image to generate

     --pullPolicy     The image pull policy
                      Available values are:
                           ALWAYS
                           IF_NOT_PRESENT
                           NEVER

     --runImage     The name of the run image to use

Description
     Builds an OCI image of the application using the output of the bootJar task

Group
     build
```

### 4. Configure build.gradle
#### 4.1 Import Type
```kotlin
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
```

#### 4.2 Configure Native Image Build Task

- [Reference for Maven Plugin](https://repo.spring.io/milestone/org/springframework/experimental/spring-graalvm-native-docs/0.8.0/spring-graalvm-native-docs-0.8.0.zip!/reference/index.html#_configure_the_maven_plugin)
 
```kotlin
tasks.getByName<BootBuildImage>("bootBuildImage") {
	builder = "paketobuildpacks/builder:tiny"
	environment = mapOf(
			"BP_BOOT_NATIVE_IMAGE" to "1",
			"BP_BOOT_NATIVE_IMAGE_BUILD_ARGUMENTS" to """
                -Dspring.spel.ignore=true                
                -Dspring.native.remove-yaml-support=true
            """.trimIndent()
	)
}
```

### 5 Bean Lite Mode
- `@SpringBootApplication(proxyBeanMethods = false)`
  - @Configuration(proxyBeanMethods = false)

`proxyBeanMethods = false` makes it not to use AspectJ with CGLIB Proxy.
This mode is called as **Bean Lite Mode**

### 6 Lightweight Embedded Tomcat
- `org.apache.tomcat.experimental:tomcat-embed-programmatic`
  - Dependency for the lightweight version of Tomcat

```kotlin
dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(group = "org.apache.tomcat.embed", module = "tomcat-embed-core")
		exclude(group = "org.apache.tomcat.embed", module = "tomcat-embed-websocket")
	}
	implementation("org.apache.tomcat.experimental:tomcat-embed-programmatic:${dependencyManagement.importedProperties["tomcat.version"]}")
}
```

## Demo
### Build Container Image
```shell script
$ ./gradlew clean bootBuildImage
```

### Run GraalVM Image
```shell script
$ docker run --rm -it -p 8080:8080 shinyay/hello:graalvm-lightweight-tomcat
```

```shell script
$ docker run --rm -it -p 8080:8080 shinyay/hello:liberica
```

## Features

## Requirement

## Usage

## Installation

## Misc

- [GraalVM with Spring Boot　Notes by Jonas Hecht](https://github.com/jonashackt/spring-boot-graalvm)

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)

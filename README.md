# Spring GraalVM Native Image

Build GraalVM Native Image by Spring Feature

## Description
### GraalVM
- [GraalVM Site](https://www.graalvm.org/)

### Dependencies
- Spring Boot Version: `Spring Boot 2.4.0-M3`
- Artifact: `org.springframework.experimental:spring-graalvm-native:0.8.2-SNAPSHOT`
  - Repository: `https://repo.spring.io/snapshot`
  - GraalVM `20.2.0`
  
### BootBuildImage Task

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

#### Configure build.gradle
##### Import Type
```kotlin
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
```

##### Configure Native Image Build Task

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

### Bean Lite Mode
- `@SpringBootApplication(proxyBeanMethods = false)`
  - @Configuration(proxyBeanMethods = false)

`proxyBeanMethods = false` makes it not to use AspectJ with CGLIB Proxy.
This mode is called as **Bean Lite Mode**

### Lightweight Embedded Tomcat
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

## Features

- feature:1
- feature:2

## Requirement

## Usage

## Installation

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)

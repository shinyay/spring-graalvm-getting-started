package com.google.shinyay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringGraalvmGettingStartedApplication

fun main(args: Array<String>) {
	runApplication<SpringGraalvmGettingStartedApplication>(*args)
}

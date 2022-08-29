package me.auth.com.authentication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(LiquibaseProperties::class)
class AuthenticationApplication

fun main(args: Array<String>) {
	runApplication<AuthenticationApplication>(*args)
}

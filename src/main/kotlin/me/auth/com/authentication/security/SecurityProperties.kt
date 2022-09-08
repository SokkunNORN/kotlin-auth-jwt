package me.auth.com.authentication.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "security", ignoreUnknownFields = true)
open class SecurityProperties(
    val jwt: Jwt = Jwt()
) {
    open class Jwt(
        var secretBase64: String = "dkdicljfosdfaosjdfoasndoifamsldjfoasmdfoajsmdlfmaosdmflasdjfoalmsd9f4kdajsddkdicljfosdfaosjdfoasndoifamsldjfoasmdfoajsmdlfmaosdmflasdjfoalmsd9f4kdajsddkdicljfosdfaosjdfoasndoifamsldjfoasmdfoajsmdlfmaosdmflasdjfoalmsd9f4kdajsd",
        var tokenExpiredLifeTime: Long = 0,
        var refreshTokenExpiredLifeTime: Long = 0,
        var refreshTokenMaxUsedTime: Int = 0
    )
}
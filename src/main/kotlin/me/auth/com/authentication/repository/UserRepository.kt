package me.auth.com.authentication.repository

import me.auth.com.authentication.domain.model.Auth
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<Auth, Long> {
    fun findByUsername(username: String): Auth?
    fun existsByUsername(username: String): Boolean
}
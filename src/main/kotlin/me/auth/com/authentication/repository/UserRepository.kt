package me.auth.com.authentication.repository

import me.auth.com.authentication.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>
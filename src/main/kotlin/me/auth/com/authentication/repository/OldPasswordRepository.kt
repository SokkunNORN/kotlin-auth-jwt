package me.auth.com.authentication.repository

import me.auth.com.authentication.domain.model.OldPassword
import org.springframework.data.jpa.repository.JpaRepository

interface OldPasswordRepository: JpaRepository<OldPassword, Long>
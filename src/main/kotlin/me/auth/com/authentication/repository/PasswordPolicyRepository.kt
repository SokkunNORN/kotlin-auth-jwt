package me.auth.com.authentication.repository

import me.auth.com.authentication.domain.model.PasswordPolicy
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordPolicyRepository: JpaRepository<PasswordPolicy, Long>
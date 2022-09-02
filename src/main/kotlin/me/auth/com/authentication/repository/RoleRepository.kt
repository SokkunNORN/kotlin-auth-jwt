package me.auth.com.authentication.repository

import me.auth.com.authentication.domain.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long>
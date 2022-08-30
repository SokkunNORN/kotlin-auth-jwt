package me.auth.com.authentication.repository

import me.auth.com.authentication.domain.model.Status
import org.springframework.data.jpa.repository.JpaRepository

interface StatusRepository: JpaRepository<Status, Long>
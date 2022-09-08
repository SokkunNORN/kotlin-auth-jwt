package me.auth.com.authentication.api.controller.auth

import me.auth.com.authentication.api.request.auth.AuthReq
import me.auth.com.authentication.api.response.helper.ok
import me.auth.com.authentication.service.auth.impl.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val service: AuthService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: AuthReq) = ok(service.login(request))
}
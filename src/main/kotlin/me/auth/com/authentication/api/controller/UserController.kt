package me.auth.com.authentication.api.controller

import me.auth.com.authentication.api.request.UserReq
import me.auth.com.authentication.api.response.UserRes
import me.auth.com.authentication.api.response.helper.ResponseWrapper
import me.auth.com.authentication.api.response.helper.ok
import me.auth.com.authentication.service.auth.IUserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val service: IUserService
) {

    @GetMapping
    fun getAll() = ok(service.getAll())

    @PostMapping("/create")
    fun create(@Validated @RequestBody user: UserReq) = ok(service.create(user))
}
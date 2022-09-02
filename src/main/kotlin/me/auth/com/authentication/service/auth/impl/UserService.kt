package me.auth.com.authentication.service.auth.impl

import me.auth.com.authentication.api.request.UserReq
import me.auth.com.authentication.api.response.UserRes
import me.auth.com.authentication.command.existsAndThrow
import me.auth.com.authentication.command.getOrElseThrow
import me.auth.com.authentication.repository.RoleRepository
import me.auth.com.authentication.repository.StatusRepository
import me.auth.com.authentication.repository.UserRepository
import me.auth.com.authentication.service.auth.IUserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepo: UserRepository,
    val statusRepo: StatusRepository,
    val roleRepo: RoleRepository
) : IUserService {
    override fun create(request: UserReq): UserRes {
        val log = LoggerFactory.getLogger(javaClass)
        log.info("The func is called...")

        val status = getOrElseThrow("Status", request.statusId!!, statusRepo::findById)
        val role = getOrElseThrow("Role", request.roleId!!, roleRepo::findById)
        userRepo.existsByUsername(request.username!!).existsAndThrow("username")

        val user = request.toModel().apply {
            this.role = role
            this.status = status
        }

        log.info("The func is called... $user")

        return userRepo.save(user).toResponse()
    }

    override fun getAll(): List<UserRes> {
        val users = userRepo.findAll()

        return users.map { it.toResponse() }
    }
}
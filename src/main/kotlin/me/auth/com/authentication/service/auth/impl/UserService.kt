package me.auth.com.authentication.service.auth.impl

import me.auth.com.authentication.api.exception.ClientErrorException
import me.auth.com.authentication.api.exception.handler.ErrorCode
import me.auth.com.authentication.api.request.UserReq
import me.auth.com.authentication.api.response.UserRes
import me.auth.com.authentication.command.helper.existsAndThrow
import me.auth.com.authentication.command.helper.getOrElseThrow
import me.auth.com.authentication.command.helper.matchPasswordPolicy
import me.auth.com.authentication.repository.PasswordPolicyRepository
import me.auth.com.authentication.repository.RoleRepository
import me.auth.com.authentication.repository.StatusRepository
import me.auth.com.authentication.repository.UserRepository
import me.auth.com.authentication.service.auth.IUserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepo: UserRepository,
    private val statusRepo: StatusRepository,
    private val roleRepo: RoleRepository,
    private val passwordPolicyRepository: PasswordPolicyRepository
) : IUserService {
    override fun create(request: UserReq): UserRes {
        val log = LoggerFactory.getLogger(javaClass)

        val status = getOrElseThrow("Status", request.statusId!!, statusRepo::findById)
        val role = getOrElseThrow("Role", request.roleId!!, roleRepo::findById)
        userRepo.existsByUsername(request.username!!).existsAndThrow("username")
        val passwordPolicy = passwordPolicyRepository.findAll().first()
        if (!request.password!!.matchPasswordPolicy(passwordPolicy)) {
            throw ClientErrorException(ErrorCode.PASSWORD_POLICY, "Incorrect password policy.")
        }

        val user = request.toModel().apply {
            this.passwordExpirationDate = LocalDateTime.now().plusDays(passwordPolicy.resetIn)
            this.role = role
            this.status = status
        }

        return userRepo.save(user).toUserResponse()
    }

    override fun getAll(): List<UserRes> {
        val users = userRepo.findAll()

        return users.map { it.toUserResponse() }
    }
}
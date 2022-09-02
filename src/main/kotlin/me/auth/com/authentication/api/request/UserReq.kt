package me.auth.com.authentication.api.request

import me.auth.com.authentication.command.getOrElseThrow
import me.auth.com.authentication.command.helper.validEmail
import me.auth.com.authentication.command.helper.validGender
import me.auth.com.authentication.command.helper.validPhone
import me.auth.com.authentication.domain.model.Auth
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

data class UserReq(
    val name: String? = null,
    val username: String? = null,
    val password: String?,
    val email: String?,
    val gender: String?,
    val phoneNumber: String?,
    val address: String?,
    val roleId: Long? = null,
    val statusId: Long? = null,
) {
    init {
        getOrElseThrow("name", name)
        getOrElseThrow("username", username)
        getOrElseThrow("password", password)
        getOrElseThrow("statusId", statusId)
        getOrElseThrow("roleId", roleId)

        gender.validGender()
        email.validEmail()
//        phoneNumber.validPhone()
    }

    fun toModel(): Auth {
        return Auth(
            name = this.name!!,
            username = this.username!!,
            password = this.password!!,
            email = this.email,
            phoneNumber = this.phoneNumber,
            gender = this.gender,
            address = this.address,
            attempt = 0L,
            isFirstLogin = true,
        )
    }
}

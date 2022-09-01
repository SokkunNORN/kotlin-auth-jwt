package me.auth.com.authentication.domain.model

import me.auth.com.authentication.api.response.UserRes
import me.auth.com.authentication.command.Extension.khFormat
import me.auth.com.authentication.domain.base.AuthorTimeStamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "auths")
data class Auth(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUser")
    @SequenceGenerator(name = "seqUser", sequenceName = "SEQ_USER", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "username", nullable = false, unique = true)
    val username: String,

    @Column(name = "PASSWORD", nullable = false)
    val password: String,

    @Column(name = "email")
    val email: String? = null,

    @Column(name = "phone_number")
    val phoneNumber: String? = null,

    @Column(name = "gender")
    val gender: String? = null,

    @Column(name = "address")
    val address: String? = null,

    @Column(name = "attempt", nullable = false)
    val attempt: Long = 0L,

    @Column(name = "freeze_date_time")
    val freezeDateTime: LocalDateTime? = null,

    @Column(name = "expiration_key_life")
    val expirationKeyLife: LocalDateTime? = null,

    @Column(name = "is_first_login")
    val isFirstLogin: Boolean = true,

    @Column(name = "password_expiration_date")
    val passwordExpirationDate: LocalDateTime? = null,

    @Column(name = "password_expiration_key")
    val passwordExpirationKey: String? = null,

    @Column(name = "registration_key")
    val registrationKey: String? = null,

    @Column(name = "registration_date_time")
    val registrationDateTime: LocalDateTime? = null,
) : AuthorTimeStamp() {
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    lateinit var status: Status

    fun toResponse(): UserRes {
        return UserRes(
            id = this.id,
            name = this.name,
            username = this.username,
            gender = this.gender,
            email = this.email,
            phoneNumber = this.phoneNumber,
            address = this.address,
            status = this.status,
            createdAt = this.createdAt.khFormat(),
            updatedAt = this.updatedAt.khFormat()
        )
    }
}

package me.auth.com.authentication.demain.model

import me.auth.com.authentication.demain.base.AuthorTimeStamp
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "access_token")
data class AccessToken(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAccessToken")
    @SequenceGenerator(name = "seqAccessToken", sequenceName = "SEQ_ACCESS_TOKEN", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "hash_token")
    val hashToken: String? = null,

    @Column(name = "device_id")
    val deviceId: String? = null,

    @Column(name = "user_agent")
    val userAgent: String? = null,

    @Column(name = "client_ip")
    val clientIp: String? = null,

    @Column(name = "last_datetime_request")
    val lastDateTimeRequest: LocalDateTime? = null,

    @Column(name = "expired_datetime")
    val expiredDateTime: LocalDateTime? = null,

    @Column(name = "refresh_token")
    val refreshToken: String? = null,

    @Column(name = "refresh_token_used_times")
    val refreshTokenUsedTimes: String? = null,

    @Column(name = "refresh_token_expired_datetime")
    val refreshTokenExpiredDateTime: LocalDateTime? = null
) : AuthorTimeStamp() {
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User? = null
}

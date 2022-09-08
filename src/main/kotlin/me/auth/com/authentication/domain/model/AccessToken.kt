package me.auth.com.authentication.domain.model

import me.auth.com.authentication.domain.base.AuthorTimeStamp
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
    var hashToken: String? = null,

    @Column(name = "device_id")
    val deviceId: String? = null,

    @Column(name = "user_agent")
    var userAgent: String? = null,

    @Column(name = "client_ip")
    var clientIp: String? = null,

    @Column(name = "last_datetime_request")
    var lastDateTimeRequest: LocalDateTime? = null,

    @Column(name = "expired_datetime")
    var expiredDateTime: LocalDateTime? = null,

    @Column(name = "refresh_token")
    var refreshToken: String? = null,

    @Column(name = "refresh_token_used_times")
    var refreshTokenUsedTimes: Int? = null,

    @Column(name = "refresh_token_expired_datetime")
    var refreshTokenExpiredDateTime: LocalDateTime? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: Auth,

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    var status: Status
) : AuthorTimeStamp() {
}

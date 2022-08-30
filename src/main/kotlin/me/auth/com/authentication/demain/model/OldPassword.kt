package me.auth.com.authentication.demain.model

import me.auth.com.authentication.demain.base.AuthorTimeStamp
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "old_password")
data class OldPassword(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqOldPassword")
    @SequenceGenerator(name = "seqOldPassword", sequenceName = "SEQ_OLD_PASSWORD", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "password", nullable = false)
    val password: String
) : AuthorTimeStamp() {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User
}
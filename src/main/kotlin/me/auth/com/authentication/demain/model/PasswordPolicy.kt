package me.auth.com.authentication.demain.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import me.auth.com.authentication.demain.base.AuthorTimeStamp
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "password_policy")
data class PasswordPolicy(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqPasswordPolicy")
    @SequenceGenerator(name = "seqPasswordPolicy", sequenceName = "SEQ_PASSWORD_POLICY", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "min_length", nullable = false)
    val minLength: Long,

    @Column(name = "min_num_lower_letter", nullable = false)
    val minNumLowerLetter: Long,

    @Column(name = "min_num_numeric", nullable = false)
    val minNumNumeric: Long,

    @Column(name = "min_num_special_char", nullable = false)
    val minNumSpecialChar: Long,

    @Column(name = "min_num_upper_letter", nullable = false)
    val minNumUpperLetter: Long,

    @Column(name = "notify_before", nullable = false)
    val notifyBefore: Long,

    @Column(name = "reset_in", nullable = false)
    val resetIn: Long
) : AuthorTimeStamp() {
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    lateinit var status: Status
}

package me.auth.com.authentication.demain.model

import me.auth.com.authentication.demain.base.AuthorTimeStamp
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "status")
data class Status(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqStatus")
    @SequenceGenerator(name = "seqStatus", sequenceName = "SEQ_STATUS", allocationSize = 10, initialValue = 10)
    val id: Long = 0L,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description")
    val description: String? = null
) : AuthorTimeStamp()

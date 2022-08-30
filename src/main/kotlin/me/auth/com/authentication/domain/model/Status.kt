package me.auth.com.authentication.domain.model

import me.auth.com.authentication.domain.base.AuthorTimeStamp
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

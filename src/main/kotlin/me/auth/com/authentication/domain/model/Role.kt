package me.auth.com.authentication.domain.model

import me.auth.com.authentication.command.RoleTypeEnum
import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqRole")
    @SequenceGenerator(name = "seqRole", sequenceName = "SEQ_ROLE", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "name")
    val name: String,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_type")
    val roleType: RoleTypeEnum
)

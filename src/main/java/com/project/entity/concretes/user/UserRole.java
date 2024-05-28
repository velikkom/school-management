package com.project.entity.concretes.user;

import com.project.entity.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class UserRole
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType roleType;

    String roleName;
}

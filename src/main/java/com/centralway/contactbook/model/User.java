package com.centralway.contactbook.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String passwordHash;
    @Transient private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private List<UserRole> roles;

    public User(Long id) {
        this.id = id;
    }
}

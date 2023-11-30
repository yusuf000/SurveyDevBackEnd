package com.example.authenticationservice.authentication.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    private Set<Privilege> privileges;
}

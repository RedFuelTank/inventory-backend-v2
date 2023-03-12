package com.netgroup.repository.client.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class RepresentativeModel {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ElementCollection
    @CollectionTable(name = "Authorities", joinColumns = @JoinColumn(name = "username"))
    @Column(name = "authority")
    private List<String> authority;
}

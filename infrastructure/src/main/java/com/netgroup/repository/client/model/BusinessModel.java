package com.netgroup.repository.client.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "businesses")
public class BusinessModel {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "representative_username")
    private RepresentativeModel representative;

    @ElementCollection
    @CollectionTable(name = "business_authorities", joinColumns = @JoinColumn(name = "name"))
    @Column(name = "authority")
    private List<String> authority;
}

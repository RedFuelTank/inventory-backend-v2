package com.netgroup.repository.client.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class RepresentativeModel {
    @Id
    private Long id;

    @Column(name = "username")
    private String username;
}

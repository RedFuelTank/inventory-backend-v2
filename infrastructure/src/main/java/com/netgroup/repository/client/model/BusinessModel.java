package com.netgroup.repository.client.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "businesses")
public class BusinessModel {
    @Id
    private Long id;

    @Column(name = "username")
    private String username;
}

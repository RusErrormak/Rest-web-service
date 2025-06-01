package org.ruserrormak.restweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table
public class Roles implements GrantedAuthority {

    @Id
    private Long id;

    @Column
    private String name;

    public Roles() {}

    public Roles(Long id) { this.id = id; }

    public Roles(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}

package org.ruserrormak.restweb.repository;

import org.ruserrormak.restweb.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByName(String name);
}

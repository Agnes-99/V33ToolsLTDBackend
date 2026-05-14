package com.v33toolsltd.repository.users;

import com.v33toolsltd.domain.users.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByEmailAddress(String emailAddress);
}

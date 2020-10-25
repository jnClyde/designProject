package ru.isu.ordermanager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isu.ordermanager.domain.model.AutoUser;

public interface ClientRepository extends JpaRepository<AutoUser, Integer> {
    AutoUser findByUsername(String username);
}

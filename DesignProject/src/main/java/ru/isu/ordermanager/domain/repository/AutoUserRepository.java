package ru.isu.ordermanager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isu.ordermanager.domain.model.AutoUser;


@Repository
public interface AutoUserRepository extends JpaRepository<AutoUser, Long> {

    public AutoUser findByUsername(String username);
}

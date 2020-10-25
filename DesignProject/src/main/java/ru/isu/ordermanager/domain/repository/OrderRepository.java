package ru.isu.ordermanager.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.isu.ordermanager.domain.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findAllByRank(Double rank, Pageable page);
    Page<Order> findAllByRankBefore(Double rank, Pageable page);
    Page<Order> findAllByRankAfter(Double rank, Pageable page);
    Page<Order> findAllByRankBetween(Double rank1, Double rank2, Pageable page);

    Page<Order> findAllByTitleIsContaining(String title, Pageable page);

    Page<Order> findAllByClientsFirstName(String client, Pageable page);
    Page<Order> findAllByClientsLastName(String client, Pageable page);
    Page<Order> findAllByClientsUsername(String client, Pageable page);

    @Override
    List<Order> findAll();


}

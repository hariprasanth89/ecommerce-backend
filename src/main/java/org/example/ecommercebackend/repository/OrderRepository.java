package org.example.ecommercebackend.repository;



import org.example.ecommercebackend.entity.Order;
import org.example.ecommercebackend.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmailOrderByCreatedAtDesc(String customerEmail);
    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);
}

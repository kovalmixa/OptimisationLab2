package edu.dnu.fpm.pz.db.Repositories;

import edu.dnu.fpm.pz.db.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderDate >= :startDate")
    BigDecimal getTotalRevenueSince(@Param("startDate") LocalDate startDate);
    @Query("SELECT o FROM Order o WHERE o.user.email = :email")
    List<Order> findByUserEmail(@Param("email") String email);
}

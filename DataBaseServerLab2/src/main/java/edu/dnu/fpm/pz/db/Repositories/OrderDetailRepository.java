package edu.dnu.fpm.pz.db.Repositories;

import edu.dnu.fpm.pz.db.Entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {}


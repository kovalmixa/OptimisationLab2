package edu.dnu.fpm.pz.db.Repositories;

import edu.dnu.fpm.pz.db.Entities.Product;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}

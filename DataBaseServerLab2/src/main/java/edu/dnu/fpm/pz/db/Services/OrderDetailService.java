package edu.dnu.fpm.pz.db.Services;

import edu.dnu.fpm.pz.db.Entities.OrderDetail;
import edu.dnu.fpm.pz.db.Repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository repo;
    public List<OrderDetail> getAll() { return repo.findAll(); }
    public OrderDetail getById(Long id) { return repo.findById(id).orElse(null); }
    public OrderDetail save(OrderDetail od) { return repo.save(od); }
}

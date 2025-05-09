package edu.dnu.fpm.pz.db.Controllers;

import edu.dnu.fpm.pz.db.Entities.OrderDetail;
import edu.dnu.fpm.pz.db.Services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/order-details")
public class OrderDetailController {
    @Autowired private OrderDetailService service;

    @GetMapping public List<OrderDetail> getAll() { return service.getAll(); }
    @PostMapping public ResponseEntity<OrderDetail> create(@RequestBody OrderDetail od) {
        return ResponseEntity.ok(service.save(od));
    }
}

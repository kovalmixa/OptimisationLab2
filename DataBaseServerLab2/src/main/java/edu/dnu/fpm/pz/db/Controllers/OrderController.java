package edu.dnu.fpm.pz.db.Controllers;

import edu.dnu.fpm.pz.db.Entities.Order;
import edu.dnu.fpm.pz.db.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService service;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAll() { return service.getAll(); }
    @GetMapping("/income")
    public BigDecimal getTotalRevenue(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from) {
        return orderService.getTotalRevenueSince(from);
    }
    @PostMapping("/generate")
    public ResponseEntity<String> generateOrders(@RequestParam int count) {
        orderService.generateOrders(count);
        return ResponseEntity.ok("Orders generated: " + count);
    }
    @GetMapping("/by-email")
    public List<Order> getOrdersByUserEmail(@RequestParam String email) {
        return orderService.findByUserEmail(email);
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order o) {
        return ResponseEntity.ok(service.save(o));
    }
}

package edu.dnu.fpm.pz.db.Services;

import com.github.javafaker.Faker;
import edu.dnu.fpm.pz.db.Entities.Order;
import edu.dnu.fpm.pz.db.Repositories.UserRepository;
import edu.dnu.fpm.pz.db.Entities.User;
import edu.dnu.fpm.pz.db.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    private final Faker faker = new Faker();

    public void generateOrders(int count) {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) return;

        for (int i = 0; i < count; i++) {
            Order order = new Order();
            order.setUser(users.get(faker.random().nextInt(users.size())));
            order.setTotalAmount(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)));
            order.setBillingAddress(faker.address().fullAddress());
            order.setShippingAddress(faker.address().fullAddress());
            order.setOrderDate(LocalDate.now());
            orderRepository.save(order);
        }
    }

    public List<Order> getAll() { return orderRepository.findAll(); }
    public Order getById(Long id) { return orderRepository.findById(id).orElse(null); }
    public Order save(Order order) { return orderRepository.save(order); }

    public BigDecimal getTotalRevenueSince(LocalDate from) {
        return orderRepository.getTotalRevenueSince(from);
    }

    public List<Order> findByUserEmail(String email) {
        return orderRepository.findByUserEmail(email);
    }
}

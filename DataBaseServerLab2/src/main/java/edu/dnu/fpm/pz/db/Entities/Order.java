package edu.dnu.fpm.pz.db.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "billing_address", nullable = false)
    private String billingAddress;

    @Column(name = "status")
    private String status;

    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDate.now();
    }

    public Order() {}

    public Order(User user, LocalDate orderDate, BigDecimal totalAmount, String shippingAddress, String billingAddress) {
        this.user = user;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public LocalDate getOrderDate() {return orderDate;}
    public void setOrderDate(LocalDate orderDate) {this.orderDate = orderDate;}

    public BigDecimal getTotalAmount() {return totalAmount;}
    public void setTotalAmount(BigDecimal totalAmount) {this.totalAmount = totalAmount;}

    public String getShippingAddress() {return shippingAddress;}
    public void setShippingAddress(String shippingAddress) {this.shippingAddress = shippingAddress;}

    public String getBillingAddress() {return billingAddress;}
    public void setBillingAddress(String billingAddress) {this.billingAddress = billingAddress;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
}

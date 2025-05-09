package edu.dnu.fpm.pz.db.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    private BigDecimal discount;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public OrderDetail() {}
    public OrderDetail(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public Product getProduct(){return product;}
    public void setProduct(Product product){this.product = product;}

    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity){this.quantity = quantity;}

    public BigDecimal getUnitPrice(){return unitPrice;}
    public void setUnitPrice(BigDecimal unitPrice){this.unitPrice = unitPrice;}

    public BigDecimal getDiscount(){return discount;}
    public void setDiscount(BigDecimal discount){this.discount = discount;}

    public BigDecimal getTotalPrice(){return totalPrice;}
    public void setTotalPrice(BigDecimal totalPrice){this.totalPrice = totalPrice;}

    public Order getOrder(){return order;}
    public void setOrder(Order order){this.order = order;}

}

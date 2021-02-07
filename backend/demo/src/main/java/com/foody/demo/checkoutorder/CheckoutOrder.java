package com.foody.demo.checkoutorder;

import com.foody.demo.account.Account;
import com.foody.demo.product.Product;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @CreationTimestamp
    private java.time.LocalDateTime purchasedAt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_orders",
            joinColumns = @JoinColumn(name = "checkout_order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;
    private Integer Price;
    @ManyToOne()
    private Account user;
}

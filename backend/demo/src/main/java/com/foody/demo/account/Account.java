package com.foody.demo.account;
import com.foody.demo.checkoutorder.CheckoutOrder;
import com.foody.demo.review.Review;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CheckoutOrder> orders;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Review> reviews;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
}


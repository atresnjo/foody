package com.foody.demo.review;
import com.foody.demo.account.Account;
import com.foody.demo.product.Product;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String text;
    private Integer rating;
    private java.time.LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    @ManyToOne()
    private Account user;
}

package com.foody.demo.product;
import com.foody.demo.productdate.ProductDate;
import com.foody.demo.review.Review;
import com.foody.demo.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String description;
    private String title;
    private Integer rating;
    private Integer price;
    private String imageUrl;
    private Boolean isTrending;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Review> reviews;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags;
    @ManyToMany(mappedBy = "products")
    private Set<ProductDate> productDates;

    public Product(String title, String description, String imageUrl, Integer price, Boolean isTrending) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.isTrending = isTrending;
    }
}

package com.foody.demo.productdate;
import com.foody.demo.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
public class ProductDate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private java.time.LocalDate value;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Product> products;

}

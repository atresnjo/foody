package com.foody.demo.product;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor

public class ProductQuery implements GraphQLQueryResolver {
    private final ProductRepository productRepository;

    public Optional<Product> product(Long id) {
        return productRepository.findById(id);
    }
    public Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }
    public Long getProductsCount () {
        return productRepository.count();
    }
}

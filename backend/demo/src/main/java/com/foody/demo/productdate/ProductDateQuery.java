package com.foody.demo.productdate;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductDateQuery implements GraphQLQueryResolver {
    private final ProductDateRepository productDateRepository;

    public Optional<ProductDate> productDate(Long id) throws InterruptedException {
        return productDateRepository.findById(id);
    }
    public Iterable<ProductDate> findAllProductDates() throws InterruptedException {
        return productDateRepository.findAll();
    }

}

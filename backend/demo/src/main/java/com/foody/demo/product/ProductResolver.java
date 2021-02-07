package com.foody.demo.product;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.lang.Math.toIntExact;

@Component
@RequiredArgsConstructor
public class ProductResolver implements GraphQLResolver<Product> {
    private final ProductRepository productRepository;

    public Integer getTotalRating(Product product) {
        var reviews = product.getReviews();
        var poorRating = reviews.stream().filter(f -> f.getRating() == 1).count();
        var belowAverageRating = reviews.stream().filter(f -> f.getRating() == 2).count();
        var averageRating = reviews.stream().filter(f -> f.getRating() == 3).count();
        var goodRating = reviews.stream().filter(f -> f.getRating() == 4).count();
        var excellentRating = reviews.stream().filter(f -> f.getRating() == 5).count();

        var calculatedRating = (5 * excellentRating + 4 * goodRating + 3 * averageRating + 2 * belowAverageRating + 1 * poorRating);
        var finalRating = (excellentRating + goodRating + averageRating + belowAverageRating);
        if (calculatedRating > 0 && finalRating > 0) {
            var result = calculatedRating / finalRating;
            return toIntExact(result);
        }
        return toIntExact(0);
    }
}


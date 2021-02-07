package com.foody.demo.review;

import com.foody.demo.account.AccountService;
import com.foody.demo.review.CreateReviewInput;
import com.foody.demo.review.Review;
import com.foody.demo.product.ProductRepository;
import com.foody.demo.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final AccountService accountService;

    @Transactional
    public boolean create(CreateReviewInput input) {
        var product = productRepository.findById(input.getProductId().longValue()).get();
        var user = accountService.getCurrentUser();
        var review = Review
                .builder()
                .user(user)
                .text(input.getText())
                .product(product)
                .rating(input.getRating())
                .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        var oldReviews = product.getReviews();
        var reviews = new HashSet<Review>() {
            {
                add(review);
            }
        };

        reviews.addAll(oldReviews);
        product.setReviews(reviews);
        reviewRepository.save(review);
        return true;
    }
}

package com.foody.demo.review;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMutation implements GraphQLMutationResolver {
    private final ReviewService reviewService;

    @PreAuthorize("isAuthenticated()")
    public boolean createReview(CreateReviewInput input) {
        return reviewService.create(input);
    }
}

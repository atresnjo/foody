package com.foody.demo.review;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class CreateReviewInput {

    private final Integer productId;
    private final String text;
    private final Integer rating;
}

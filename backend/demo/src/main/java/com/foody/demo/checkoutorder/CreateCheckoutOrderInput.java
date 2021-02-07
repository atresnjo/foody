package com.foody.demo.checkoutorder;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class CreateCheckoutOrderInput {
        private final Integer[] productIds;
}

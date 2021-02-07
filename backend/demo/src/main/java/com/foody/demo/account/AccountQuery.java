package com.foody.demo.account;

import com.foody.demo.checkoutorder.CheckoutOrder;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class AccountQuery implements GraphQLQueryResolver {
    private final AccountService accountService;

    @PreAuthorize("isAuthenticated()")
    public Account me() {
        var user = accountService.getCurrentUser();
        user.getOrders().sort(Comparator.comparing(CheckoutOrder::getPurchasedAt, Comparator.reverseOrder()));
        return user;
    }
}

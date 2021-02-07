package com.foody.demo.account;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountResolver implements GraphQLResolver<Account> {
    private final AccountService service;

    @PreAuthorize("isAuthenticated()")
    public String getToken(Account user) {
        return service.getToken(user);
    }
}


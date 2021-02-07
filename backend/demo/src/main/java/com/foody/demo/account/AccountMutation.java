package com.foody.demo.account;

import com.foody.demo.checkoutorder.CreateCheckoutOrderInput;
import com.foody.demo.security.SecurityProperties;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

@Component
@RequiredArgsConstructor
public class AccountMutation implements GraphQLMutationResolver {
    private final AuthenticationProvider authenticationProvider;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final SecurityProperties properties;

    public Account createAccount(String email, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(properties.getPasswordStrength());
        var account = new Account(email, encoder.encode(password));
        accountRepository.save(account);

        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(credentials));
            out.println("get user");
            return accountService.getCurrentUser();
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(email);
        }
    }

    public Account login(String email, String password) {
        out.println("Login attempt");
        out.println(email);
        out.println(password);

        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(credentials));
            out.println("get user");
            return accountService.getCurrentUser();
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(email);
        }
    }

    @PreAuthorize("isAuthenticated()")
    public Boolean createCheckoutOrder(CreateCheckoutOrderInput orderInfo) throws InterruptedException {
        out.println("Creating checkout!");
        return accountService.createCheckoutOrder(orderInfo);
    }
}

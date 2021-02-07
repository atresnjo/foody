package com.foody.demo.account;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.foody.demo.checkoutorder.CheckoutOrder;
import com.foody.demo.checkoutorder.CheckoutOrderRepository;
import com.foody.demo.checkoutorder.CreateCheckoutOrderInput;
import com.foody.demo.exceptions.BadTokenException;
import com.foody.demo.product.ProductRepository;
import com.foody.demo.security.JwtUserDetails;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static com.foody.demo.misc.Utils.convert;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final CheckoutOrderRepository checkoutOrderRepository;
    private final ProductRepository productRepository;
    private final JWTVerifier verifier;
    private final Algorithm algorithm;

    @Override
    @Transactional
    public JwtUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository
                .findByEmail(email)
                .map(user -> getUserDetails(user, getToken(user)))
                .orElseThrow(() -> new UsernameNotFoundException("Username or password didn't match"));
    }

    @Transactional
    public String getToken(Account user) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(Duration.ofHours(2)); // Token will be valid for 2 hours
        return JWT
                .create()
                .withIssuer("http://localhost:9001") // Same as within the JWTVerifier
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(user.getEmail())
                .sign(algorithm); // Same algorithm as within the JWTVerifier
    }

    @Transactional
    public JwtUserDetails loadUserByToken(String token) {
        return getDecodedToken(token)
                .map(DecodedJWT::getSubject)
                .flatMap(accountRepository::findByEmail)
                .map(user -> getUserDetails(user, token))
                .orElseThrow(BadTokenException::new);
    }

    private JwtUserDetails getUserDetails(Account user, String token) {
        return JwtUserDetails
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .token(token)
                .build();
    }

    private Optional<DecodedJWT> getDecodedToken(String token) {
        try {
            return Optional.of(verifier.verify(token));
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    @Transactional
    public boolean createCheckoutOrder(CreateCheckoutOrderInput input) {
        var productIds = convert(input.getProductIds());
        var productIdsIterable = convert(productIds);
        var allProducts = productRepository.findAllById(productIdsIterable);
        var user = getCurrentUser();
        var order = CheckoutOrder
                .builder()
                .user(user)
                .products(Sets.newHashSet(allProducts))
                .Price(allProducts.stream().map(x -> x.getPrice()).reduce(0, Integer::sum))
                .build();

        var orders = new ArrayList<CheckoutOrder>() {
            {
                add(order);
            }
        };

        orders.addAll(user.getOrders());
        user.setOrders(orders);
        checkoutOrderRepository.save(order);
        accountRepository.save(user);
        return true;
    }

    @Transactional
    public Account getCurrentUser() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .flatMap(accountRepository::findByEmail)
                .orElse(null);
    }
}

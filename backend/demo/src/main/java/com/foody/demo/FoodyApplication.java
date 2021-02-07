package com.foody.demo;

import com.foody.demo.account.Account;
import com.foody.demo.account.AccountRepository;
import com.foody.demo.checkoutorder.CheckoutOrderRepository;
import com.foody.demo.product.Product;
import com.foody.demo.product.ProductRepository;
import com.foody.demo.productdate.ProductDate;
import com.foody.demo.productdate.ProductDateRepository;
import com.foody.demo.review.ReviewRepository;
import com.foody.demo.security.SecurityProperties;
import com.foody.demo.tag.Tag;
import com.foody.demo.tag.TagRepository;
import com.github.javafaker.Faker;
import com.google.common.collect.Iterables;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static java.lang.System.out;

@SpringBootApplication
@RequiredArgsConstructor

public class FoodyApplication {

    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    private final ProductDateRepository productDateRepository;
    private final AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(FoodyApplication.class, args);
    }

    @Bean
    InitializingBean seedAccount(SecurityProperties properties) {
        return () -> {
            var count = Iterables.size(accountRepository.findAll());
            if (count > 0)
                return;

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(properties.getPasswordStrength());
            var password = encoder.encode("test123");

            out.println("Saving accounts");
            accountRepository.save(new Account("test@test.com", password));
        };
    }

    @Bean
    InitializingBean seedTags() {
        return () -> {
            var count = Iterables.size(tagRepository.findAll());
            if (count > 0)
                return;

            out.println("Saving tags");
            tagRepository.save(new Tag("Vegan"));
            tagRepository.save(new Tag("Bio"));
            tagRepository.save(new Tag("Meat"));
            tagRepository.save(new Tag("Dessert"));
            tagRepository.save(new Tag("Healthy"));
            tagRepository.save(new Tag("Gluten free"));
        };
    }

    @Bean
    InitializingBean seedProducts() {
        return () -> {
            var count = Iterables.size(productRepository.findAll());
            if (count > 0)
                return;

            out.println("Saving products");

            productRepository.save(new Product("Crunchy stir fry featuring fresh thyme and free range chicken", "Thyme and chicken stir fry", "https://images.unsplash.com/photo-1567575990843-105a1c70d76e?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=934&q=80", 2, true));
            productRepository.save(new Product("Apricot ice cream with butter", "Creamy, apricot ice cream served with a rich butter sauce", "https://images.unsplash.com/photo-1476718406336-bb5a9690ee2a?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=934&q=80", 3, false));
            productRepository.save(new Product("Plantain and bean madras", "Medium-hot madras made with fresh plantain and bean", "https://images.unsplash.com/photo-1547714695-bed5d1fef848?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=934&q=80", 4, true));
            productRepository.save(new Product("Chicken and turkey panini", "A hot, pressed panini filled with free range chicken and smoked turkey", "https://images.unsplash.com/photo-1568158879083-c42860933ed7?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=933&q=80", 7, false));
            productRepository.save(new Product("Poussin and feta salad", "Poussin and tangy feta served on a bed of lettuce", "https://images.unsplash.com/photo-1526401363794-c96708fb8089?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=934&q=80", 7, true));

            var tags = tagRepository.findAll();
            var products = productRepository.findAll();
            out.println(Iterables.size(products));
            List<Tag> productTags = new ArrayList();
            tags.forEach(productTags::add);
            var random = new Random();
            for (Product product : products) {
                Collections.shuffle(productTags);
                var set = new HashSet<Tag>();
                for (int i = 0; i < random.nextInt((productTags.size() - 1) + 1); i++) {
                    set.add(productTags.get(i));
                }
                product.setTags(set);
            }

            productRepository.saveAll(products);
        };
    }

    @Bean
    InitializingBean seedDays() {
        return () -> {
            var count = Iterables.size(productDateRepository.findAll());
            if (count > 0)
                return;

            out.println("Saving days");
            var beginDate = LocalDate.now();
            var endDate = beginDate.plusDays(7);

            var product = productRepository.findAll();
            var allDates = beginDate.datesUntil(endDate);
            List<Product> products = new ArrayList();
            product.forEach(products::add);
            var random = new Random();

            allDates.forEach(actualDate -> {
                out.println(actualDate);
                Collections.shuffle(products);
                var productDate = new ProductDate();
                productDate.setValue(actualDate);
                var set = new HashSet<Product>();
                var randomNumber = random.nextInt((products.size() - 1) + 1);
                for (int i = 0; i < randomNumber; i++) {
                    set.add(products.get(i));
                }

                productDate.setProducts(set);
                productDateRepository.save(productDate);
            });

        };
    }
}

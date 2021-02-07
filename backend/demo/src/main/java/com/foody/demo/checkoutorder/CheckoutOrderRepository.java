package com.foody.demo.checkoutorder;

import com.foody.demo.checkoutorder.CheckoutOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutOrderRepository extends JpaRepository<CheckoutOrder, Long> {
}

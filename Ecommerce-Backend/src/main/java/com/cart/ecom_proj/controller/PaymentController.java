package com.cart.ecom_proj.controller;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin // your React app URL
@RequestMapping("/api/payment")
public class PaymentController {

    

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostMapping("/create-payment-intent")
    public Map<String, Object> createPaymentIntent(@RequestBody Map<String, Object> data) throws Exception {
        Stripe.apiKey = stripeSecretKey;
System.out.println("Stripe Key: " + stripeSecretKey);
System.out.println("Amount: " + data.get("amount"));
        int amount = (int) data.get("amount"); // in cents (e.g. 1000 = $10)

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", "usd");
        params.put("automatic_payment_methods", Map.of("enabled", true));

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        Map<String, Object> response = new HashMap<>();
        response.put("clientSecret", paymentIntent.getClientSecret());
        return response;
    }
}
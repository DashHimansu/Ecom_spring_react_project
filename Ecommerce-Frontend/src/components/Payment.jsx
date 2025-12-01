import React, { useEffect, useState } from "react";
import { loadStripe } from "@stripe/stripe-js";
import {
  Elements,
  CardElement,
  useStripe,
  useElements,
} from "@stripe/react-stripe-js";
import axios from "axios";
import { useLocation } from "react-router-dom";


const stripePromise = loadStripe("pk_test_51OuqFxCmm4LNA3ZHrMM0fQ3X0M0Qevqe5nguaK4OSPKthfzDIqLwD3gmBqvuAxghwyGJcAjfjbSuTjsZCy7MbMr000gQfl6XTv"); // your publishable key

const CheckoutForm = () => {
  const stripe = useStripe();
  const elements = useElements();
  const [clientSecret, setClientSecret] = useState("");
  const location = useLocation();
  const { cartItems } = location.state || {}; 
  console.log(cartItems);
  useEffect(() => {
    // Create PaymentIntent on backend
    axios
      .post("http://localhost:8091/api/payment/create-payment-intent", {
        amount: 1000, // $10 (1000 cents)
      })
      .then((res) => setClientSecret(res.data.clientSecret))
      .catch((err) => console.log(err));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!stripe || !elements) return;

    const card = elements.getElement(CardElement);

    const { error, paymentIntent } = await stripe.confirmCardPayment(
      clientSecret,
      { payment_method: { card } }
    );

    if (error) {
      alert(error.message);
    } else if (paymentIntent.status === "succeeded") {
      alert("Payment successful! ✅");
      console.log(paymentIntent);
    }
  };

  return (
    <>
     <div className="cart-container">
      <div className="shopping-cart">
        <div className="container mt-4">
  <h3 className="mb-4">🛒 Order Summary</h3>

  {cartItems && cartItems.length > 0 ? (
    <>
      {cartItems.map((item) => (
        <div
          key={item.cartId}
          className="d-flex justify-content-between align-items-center border-bottom py-3"
        >
          {/* Left side: image + name */}
          <div className="d-flex align-items-center">
            <div>
              <h6 className="mb-1">{item.product.name}</h6>
              <small className="text-muted">Qty: {item.quantity}</small>
            </div>
          </div>

          {/* Right side: price */}
          <div>
            <h6 className="text-success mb-0">
              ₹{(item.product.price * item.quantity).toLocaleString()}
            </h6>
          </div>
        </div>
      ))}

      {/* Bottom Total */}
      <div className="d-flex justify-content-between align-items-center mt-4 p-3 border-top">
        <h5 className="mb-0">Total Amount</h5>
        <h4 className="text-success mb-0">
          ₹
          {cartItems
            .reduce(
              (sum, item) => sum + item.product.price * item.quantity,
              0
            )
            .toLocaleString()}
        </h4>
      </div>
    </>
  ) : (
    <p className="text-muted text-center mt-5">Your cart is empty 😢</p>
  )}
</div>

<form className="row g-3 pt-5" onSubmit={handleSubmit}>
  <div className="col-12">
    <div style={{ border: "1px solid #ccc", padding: "10px", borderRadius: "6px" }}>
      <CardElement />
    </div>
  </div>
  <div className="col-12">
    <button
      type="submit"
      disabled={!stripe}
      className="btn btn-primary"
      style={{ marginTop: "1rem", width: "100%" }}
    >
      Pay Now
    </button>
  </div>
</form>
      </div>
      </div>
    </>
    
  );
};

const Payment = () => (
  <Elements stripe={stripePromise}>
    <CheckoutForm />
  </Elements>
);

export default Payment;

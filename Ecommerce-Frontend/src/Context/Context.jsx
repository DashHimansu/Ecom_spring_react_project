import axios from "../axios";
import { useState, useEffect, createContext } from "react";

const AppContext = createContext({
  data: [],
  isError: "",
  cart: [],
  addToCart: (product) => {},
  removeFromCart: (productId) => {},
  refreshData:() =>{},
  updateStockQuantity: (productId, newQuantity) =>{}
  
});

export const AppProvider = ({ children }) => {
  const [data, setData] = useState([]);
  const [isError, setIsError] = useState("");
  const [cart, setCart] = useState([]);


  const addToCart = (product) => {
    const existingProductIndex = cart.findIndex((item) => item.id === product.id);
    if (existingProductIndex !== -1) {
      const updatedCart = cart.map((item, index) =>
        index === existingProductIndex
          ? { ...item, quantity: item.quantity + 1 }
          : item
      );
      setCart(updatedCart);
      console.log("ccart")
      //localStorage.setItem('cart', JSON.stringify(updatedCart));
      axios.post(`/add-to-cart/${product.id}`)
      .then(res => console.log("Cart: " + res.data))
      .catch(err => console.error(err));
    } else {
      console.log("ccart")
      const updatedCart = [...cart, { ...product, quantity: 1 }];
      setCart(updatedCart);
      //localStorage.setItem('cart', JSON.stringify(updatedCart));
      axios.post(`/add-to-cart/${product.id}`)
      .then(res => console.log("Cart: " + res.data))
      .catch(err => console.error(err));
        }
  };

  const removeFromCart = (productId) => {
    console.log("productID",productId)
    const updatedCart = cart.filter((item) => item.id !== productId);
    setCart(updatedCart);
    localStorage.setItem('cart', JSON.stringify(updatedCart));
    console.log("CART",cart)
  };

  const refreshData = async () => {
    try {
      const response = await axios.get("/products");
      setData(response.data);
    } catch (error) {
      setIsError(error.message);
    }
  };

  const clearCart =() =>{
    setCart([]);
  }
  
  useEffect(() => {
    console.log("ll")
    // Fetch cart from backend
    axios.get("/get-cart")
      .then(res => {
        console.log("cartv"+res)
        setCart(res.data); // Set state from DB
      })
      .catch(err => console.error(err));
  }, []);

  useEffect(() => {
    localStorage.setItem('cart', JSON.stringify(cart));
  }, [cart]);
  
  return (
    <AppContext.Provider value={{ data, isError, cart, addToCart, removeFromCart,refreshData, clearCart  }}>
      {children}
    </AppContext.Provider>
  );
};

export default AppContext;
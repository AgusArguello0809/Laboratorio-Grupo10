import React from "react";
import { Routes, Route } from "react-router-dom";
import Login from "../features/auth/pages/Login";
import Home from "../features/product/pages/Home";
import Navbar from "../features/shared/components/layout/Navbar";
import Carrito from "../features/cart/pages/Carrito";
import Register from "../features/auth/pages/Register";
import MyPublications from "../features/product/pages/MyPublications";
import SellProduct from "../features/product/pages/SellProduct";
import About from "../features/about/pages/About";
import { ProductProvider } from "../features/product/context/ProductContext";
import PrivateRoute from "../features/auth/components/PrivateRoute";
import { CartProvider } from "../features/cart/context/CartContext";

function App() {
  return (
    <CartProvider>
      <ProductProvider>
        <Navbar />
        <Routes>
          {/* Rutas públicas */}
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/us" element={<About />} />

          {/* Rutas protegidas */}
          <Route path="/cart" element={
            <PrivateRoute>
              <Carrito />
            </PrivateRoute>
          } />

          <Route path="/my-publications" element={
            <PrivateRoute>
              <MyPublications />
            </PrivateRoute>
          } />

          <Route path="/sell" element={
            <PrivateRoute>
              <SellProduct />
            </PrivateRoute>
          } />
        </Routes>
      </ProductProvider>
    </CartProvider>
  );
}

export default App;
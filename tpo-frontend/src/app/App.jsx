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
import { ProductProvider } from "../features/context/ProductContext";

function App() {
  return (
    <>
      <ProductProvider>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/cart" element={<Carrito />} />
          <Route path="/my-publications" element={<MyPublications />} />
          <Route path="/sell" element={<SellProduct />} />
          <Route path="/us" element={<About />} />
        </Routes>
      </ProductProvider>
    </>
  );
}

export default App;

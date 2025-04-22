import React from "react";
import { Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import Home from "./components/Home";
import Navbar from "./components/Navbar";
import Carrito from "./components/Carrito"; // ✅ Tu carrito
import Register from "./components/Register"; // ✅ Del grupo

function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/cart" element={<Carrito />} />
      </Routes>
    </>
  );
}

export default App;

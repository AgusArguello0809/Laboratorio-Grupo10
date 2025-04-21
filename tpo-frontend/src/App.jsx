// App.jsx
import React from "react";
import { Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import Home from "./components/Home";
import Navbar from "./components/Navbar";
import Carrito from "./components/carrito";

function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/cart" element={<Carrito />} />
      </Routes>
    </>
  );
}

export default App;

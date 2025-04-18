import React from "react";
import { Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Home from "./pages/Home";
import Navbar from "./components/Navbar";
import MyPublications from "./pages/MyPublications";
import SellProduct from "./pages/SellProduct";
import About from "./pages/About"

function App() {
  return (
    <div>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/my-publications" element={<MyPublications />} />
        <Route path="/sell" element={<SellProduct />} />
        <Route path="/us" element={<About/>} />
      </Routes>
    </div>
  );
}

export default App;

import React from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import { UserProvider } from "../features/auth/context/AuthProvider";
import App from "./App.jsx";
import "../index.css";
import { CarritoProvider } from "../features/cart/context/CarritoProvider.jsx";

const root = createRoot(document.getElementById("root"));

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <UserProvider>
        <CarritoProvider>
          <App />
        </CarritoProvider>
      </UserProvider>
    </BrowserRouter>
  </React.StrictMode>
);

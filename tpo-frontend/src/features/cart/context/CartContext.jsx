import { createContext, useContext, useState, useEffect } from "react";
import { useAuth } from "../../auth/context/AuthContext";

const CartContext = createContext();
export const useCarrito = () => useContext(CartContext);

export const CartProvider = ({ children }) => {
  const { user, isInitialized } = useAuth();
  const [carrito, setCarrito] = useState([]);
  const [carritoCargado, setCarritoCargado] = useState(false);

  useEffect(() => {
    console.log("useEffect de CartProvider ejecutado");
    console.log("isInitialized:", isInitialized);
    console.log("user:", user);

    if (!isInitialized) return;

    if (user) {
      const key = `carrito_${user.id}`;
      const guardado = JSON.parse(localStorage.getItem(key)) || [];
      console.log(`Leyendo carrito para key: ${key}`, guardado);
      setCarrito(guardado);
    } else {
      console.log("No hay user. Vaciando carrito");
      setCarrito([]);
    }

    setCarritoCargado(true);
  }, [isInitialized, user]);

  // Guardar carrito si cambió y ya se cargó
  useEffect(() => {
    if (carritoCargado && user) {
      const key = `carrito_${user.id}`;
      console.log(`Guardando carrito para key: ${key}`, carrito);
      localStorage.setItem(key, JSON.stringify(carrito));
    }
  }, [carrito, user, carritoCargado]);

  useEffect(() => {
    if (isInitialized && !user) {
      setCarrito([]);
      setCarritoCargado(true); // por si vuelve a montar el componente sin reiniciar
    }
  }, [user, isInitialized]);

  const cantidadTotal = carrito.reduce((acc, item) => acc + item.cantidad, 0);

  // Mientras no está inicializado o no se cargó el carrito, no renderizar nada
  if (!isInitialized || !carritoCargado) return null;

  return (
    <CartContext.Provider value={{ carrito, setCarrito, cantidadTotal }}>
      {children}
    </CartContext.Provider>
  );
};
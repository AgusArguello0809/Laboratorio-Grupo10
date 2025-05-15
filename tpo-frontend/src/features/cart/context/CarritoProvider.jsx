import { createContext, useContext, useState, useEffect } from "react";
import { useUser } from "../../auth/context/AuthProvider";

const CarritoContext = createContext();
export const useCarrito = () => useContext(CarritoContext);

export const CarritoProvider = ({ children }) => {
  const { user, isInitialized } = useUser();
  const [carrito, setCarrito] = useState([]);

  // Cargar carrito solo si el user está cargado
  useEffect(() => {
    if (isInitialized && user) {
      const key = `carrito_${user.id}`;
      const guardado = JSON.parse(localStorage.getItem(key)) || [];
      setCarrito(guardado);
    }
  }, [user, isInitialized]);

  // Guardar carrito solo si user está cargado
  useEffect(() => {
    if (isInitialized && user) {
      const key = `carrito_${user.id}`;
      localStorage.setItem(key, JSON.stringify(carrito));
    }
  }, [carrito, user, isInitialized]);

  const cantidadTotal = carrito.reduce((acc, item) => acc + item.cantidad, 0);

  return (
    <CarritoContext.Provider value={{ carrito, setCarrito, cantidadTotal }}>
      {children}
    </CarritoContext.Provider>
  );
};

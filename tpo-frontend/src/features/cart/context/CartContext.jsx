import { createContext, useContext, useState, useEffect } from "react";
import { useAuth } from "../../auth/context/AuthContext";
import { getToken } from "../../auth/services/authService";

const CartContext = createContext();
export const useCarrito = () => useContext(CartContext);

export const CartProvider = ({ children }) => {
  const { user, isInitialized } = useAuth();
  const [carrito, setCarrito] = useState({ id: null, productos: [] });
  const [carritoCargado, setCarritoCargado] = useState(false);

  const API_BASE_URL = "http://localhost:8080/fitstore-api/v1";
  const token = getToken();

  const fetchCarrito = async () => {
    if (!user?.id || fetchCarrito.loading) return;

    fetchCarrito.loading = true;

    try {
      const res = await fetch(`${API_BASE_URL}/carritos`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!res.ok) throw new Error("Fallo al obtener el carrito");

      const data = await res.json();
      const productos = data.productos || [];

      const productosValidados = await Promise.all(
        productos.map(async (item) => {
          if (!item.productoId) return null;

          try {
            const res = await fetch(`${API_BASE_URL}/productos/${item.productoId}`, {
              headers: { Authorization: `Bearer ${token}` },
            });
            if (!res.ok) return null;
            const producto = await res.json();

            return {
              ...item,
              title: producto.title,
              price: parseFloat(producto.price) || 0,
              stock: producto.stock,
              ownerId: producto.ownerId,
              images: producto.images || [],
            };
          } catch {
            return null;
          }
        })
      );

      const productosFiltrados = productosValidados.filter(
        (item) =>
          item &&
          item.productoId &&
          typeof item.title === "string" &&
          !isNaN(item.price) &&
          !isNaN(item.stock) &&
          !isNaN(item.cantidad)
      );

      const nuevoCarrito = {
        id: data.id ?? null,
        productos: productosFiltrados,
      };

      setCarrito(nuevoCarrito);
      localStorage.setItem(`carrito_${user.id}`, JSON.stringify(nuevoCarrito));
    } catch (err) {
      console.error("🛑 Error al cargar carrito:", err);
      const local = JSON.parse(localStorage.getItem(`carrito_${user.id}`)) || {
        id: null,
        productos: [],
      };
      setCarrito(local);
    } finally {
      setCarritoCargado(true);
      fetchCarrito.loading = false;
    }
  };

  fetchCarrito.loading = false;

  useEffect(() => {
    if (isInitialized && user?.id) {
      fetchCarrito();
    }
  }, [isInitialized, user?.id]);

  useEffect(() => {
    if (carritoCargado && user) {
      localStorage.setItem(`carrito_${user.id}`, JSON.stringify(carrito));
    }
  }, [carrito, user, carritoCargado]);

  const cantidadTotal = Array.isArray(carrito.productos)
    ? carrito.productos.reduce((acc, item) => acc + (item.cantidad || 0), 0)
    : 0;

  return (
    <CartContext.Provider
      value={{
        carrito,
        setCarrito,
        cantidadTotal,
        fetchCarrito,
        carritoCargado,
        isInitialized,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};

import { createContext, useContext, useState, useEffect } from "react";
import axios from "axios";

const ProductContext = createContext();

export const ProductProvider = ({ children }) => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchProducts = async () => {
    setLoading(true);
    try {
      const response = await axios.get("http://localhost:3001/products");
      setProducts(response.data);
      setError(null);
    } catch (err) {
      console.error("Error fetching products:", err);
      setError("Error al cargar los productos");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const addProduct = async (newProduct) => {
    try {
      const response = await axios.post(
        "http://localhost:3001/products", 
        newProduct
      );
      setProducts(prevProducts => [...prevProducts, response.data]);
      return { success: true, data: response.data };
    } catch (err) {
      console.error("Error adding product:", err);
      return { success: false, error: "Error al añadir producto" };
    }
  };

  const updateProduct = async (updatedProduct) => {
    try {
      const response = await axios.patch(
        `http://localhost:3001/products/${updatedProduct.id}`, 
        updatedProduct
      );
      setProducts(prevProducts => 
        prevProducts.map(product => 
          product.id === updatedProduct.id ? response.data : product
        )
      );
      return { success: true, data: response.data };
    } catch (err) {
      console.error("Error updating product:", err);
      return { success: false, error: "Error al actualizar producto" };
    }
  };

  const deleteProduct = async (productId) => {
    try {
      await axios.delete(`http://localhost:3001/products/${productId}`);
      setProducts(prevProducts => 
        prevProducts.filter(product => product.id !== productId)
      );
      return { success: true };
    } catch (err) {
      console.error("Error deleting product:", err);
      return { success: false, error: "Error al eliminar producto" };
    }
  };

  const getProductsByOwner = (ownerId) => {
    return products.filter(product => product.ownerId === ownerId);
  };

  const value = {
    products,
    loading,
    error,
    fetchProducts,
    addProduct,
    updateProduct,
    deleteProduct,
    getProductsByOwner
  };

  return (
    <ProductContext.Provider value={value}>
      {children}
    </ProductContext.Provider>
  );
};

export const useProducts = () => {
  const context = useContext(ProductContext);
  if (!context) {
    throw new Error("useProducts debe usarse dentro de un ProductProvider");
  }
  return context;
};
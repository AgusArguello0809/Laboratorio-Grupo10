import { useEffect } from "react";
import axios from "axios";
import { useProductContext } from "../context/ProductContext";

export const useProductService = () => {
  const {
    products,
    setProducts,
    loading,
    setLoading,
    error,
    setError
  } = useProductContext();

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    setLoading(true);
    try {
      const response = await axios.get("http://localhost:3001/products");
      setProducts(response.data);
      setError(null);
    } catch (err) {
      console.error("Error al cargar los productos:", err);
      setError("Error al cargar los productos");
    } finally {
      setLoading(false);
    }
  };

  const addProduct = async (newProduct) => {
    setLoading(true);
    try {
      const response = await axios.post(
        "http://localhost:3001/products",
        newProduct
      );
      setProducts(prevProducts => [...prevProducts, response.data]);
      setLoading(false);
      return { success: true, data: response.data };
    } catch (err) {
      console.error("Error al añadir producto:", err);
      setError("Error al añadir producto");
      setLoading(false);
      return { success: false, error: err.message };
    }
  };
  const updateProduct = async (updatedProduct) => {
    setLoading(true);
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
      setLoading(false);
      return { success: true, data: response.data };
    } catch (err) {
      console.error("Error al actualizar producto:", err);
      setError("Error al actualizar producto");
      setLoading(false);
      return { success: false, error: err.message };
    }
  };

  const deleteProduct = async (productId) => {
    setLoading(true);
    try {
      await axios.delete(`http://localhost:3001/products/${productId}`);
      setProducts(prevProducts =>
        prevProducts.filter(product => product.id !== productId)
      );
      setLoading(false);
      return { success: true };
    } catch (err) {
      console.error("Error al eliminar producto:", err);
      setError("Error al eliminar producto");
      setLoading(false);
      return { success: false, error: err.message };
    }
  };

  const getProductsByOwner = (ownerId) => {
    return products.filter(product => product.ownerId === ownerId);
  };

  const filterProducts = (searchTerm = "", stockFilter = "all", category = "all") => {
    return products.filter(product => {
      const matchesSearch = product.title
        .toLowerCase()
        .includes(searchTerm.toLowerCase());

      let matchesStock = true;
      if (stockFilter === "inStock") {
        matchesStock = product.stock > 0;
      } else if (stockFilter === "outOfStock") {
        matchesStock = product.stock === 0;
      }

      const matchesCategory =
        category === "all" || product.category === category;

      return matchesSearch && matchesStock && matchesCategory;
    });
  };

  const sortProducts = (productsToSort, sortBy = "titleAsc") => {
    return [...productsToSort].sort((a, b) => {
      switch (sortBy) {
        case "titleAsc":
          return a.title.localeCompare(b.title);
        case "titleDesc":
          return b.title.localeCompare(a.title);
        case "priceLow":
          return parseFloat(a.price) - parseFloat(b.price);
        case "priceHigh":
          return parseFloat(b.price) - parseFloat(a.price);
        default:
          return a.title.localeCompare(b.title);
      }
    });
  };

  return {
    products,
    loading,
    error,
    fetchProducts,
    addProduct,
    updateProduct,
    deleteProduct,
    getProductsByOwner,
    filterProducts,
    sortProducts
  };
};
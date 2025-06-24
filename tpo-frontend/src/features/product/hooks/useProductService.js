import { useEffect } from "react";
import axios from "axios";
import { useProductContext } from "../context/ProductContext";
import { getToken } from "../../auth/services/authService";

const API_BASE_URL = "http://localhost:8080/fitstore-api/v1";

const getAuthHeaders = () => {
  const token = getToken();
  return {
    'Content-Type': 'application/json',
    ...(token && { Authorization: `Bearer ${token}` })
  };
};

const processProductImages = (images) => {
  if (!images || images.length === 0) {
    return [];
  }

  return images.map((imageItem, index) => {
    if (imageItem && imageItem.file) {
      return imageItem.file;
    } else if (imageItem instanceof File) {
      return imageItem;
    } else {
      console.warn(`Imagen ${index} no tiene formato válido:`, imageItem);
      return null;
    }
  }).filter(file => file !== null);
};

const createFormDataWithProduct = (productData, processedImages) => {
  const formData = new FormData();
  
  const productPayload = {
    title: productData.title,
    description: productData.description,
    price: productData.price,
    stock: productData.stock,
    categoryId: productData.categoryId || productData.category || 1
  };
  
  formData.append('data', JSON.stringify(productPayload));
  
  processedImages.forEach(file => {
    formData.append('images', file);
  });
  
  return formData;
};

const sendFormDataRequest = async (url, method, formData, token) => {
  const response = await fetch(url, {
    method,
    headers: {
      ...(token && { Authorization: `Bearer ${token}` })
    },
    body: formData
  });
  
  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`HTTP ${response.status}: ${errorText}`);
  }
  
  return await response.json();
};

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
      const response = await axios.get(`${API_BASE_URL}/productos`, {
        headers: getAuthHeaders()
      });
      
      let productosArray = [];
      if (Array.isArray(response.data)) {
        productosArray = response.data;
      } else if (response.data && Array.isArray(response.data.content)) {
        productosArray = response.data.content;
      } else if (response.data && Array.isArray(response.data.productos)) {
        productosArray = response.data.productos;
      }
      
      setProducts(productosArray);
      setError(null);
    } catch (err) {
      console.error("Error al cargar los productos:", err);
      
      if (err.response?.status === 401) {
        setError("Tu sesión ha expirado. Por favor, inicia sesión nuevamente.");
      } else if (err.response?.status === 403) {
        setError("No tienes permisos para ver los productos.");
      } else {
        setError("Error al cargar los productos");
      }
      
      setProducts([]);
    } finally {
      setLoading(false);
    }
  };

  const addProduct = async (newProduct) => {
    setLoading(true);
    try {
      const token = getToken();
      const processedImages = processProductImages(newProduct.images);
      
      if (processedImages.length === 0) {
        throw new Error("Debe agregar al menos una imagen para el producto");
      }
      
      const formData = createFormDataWithProduct(newProduct, processedImages);
      const data = await sendFormDataRequest(
        `${API_BASE_URL}/productos`,
        'POST',
        formData,
        token
      );
      console.log(data)
      
      setProducts(prevProducts => [...prevProducts, data]);
      setError(null);
      return { success: true, data: data };
      
    } catch (err) {
      console.error("Error en addProduct:", err);
      setError("Error al añadir producto: " + err.message);
      return { success: false, error: err.message };
    } finally {
      setLoading(false);
    }
  };

  const updateProduct = async (updatedProduct) => {
    setLoading(true);
    try {
      const token = getToken();
      
      // Si el producto tiene imágenes nuevas, usar FormData
      if (updatedProduct.images && updatedProduct.images.length > 0) {
        const processedImages = processProductImages(updatedProduct.images);
        
        if (processedImages.length === 0) {
          throw new Error("Debe agregar al menos una imagen para el producto");
        }
        
        const formData = createFormDataWithProduct(updatedProduct, processedImages);
        const data = await sendFormDataRequest(
          `${API_BASE_URL}/productos/${updatedProduct.id}`,
          'PUT',
          formData,
          token
        );
        
        setProducts(prevProducts =>
          prevProducts.map(product =>
            product.id === updatedProduct.id ? data : product
          )
        );
        
        return { success: true, data: data };
      } else {
        // Si no hay imágenes nuevas, usar JSON (mantener imágenes existentes)
        const response = await axios.put(
          `${API_BASE_URL}/productos/${updatedProduct.id}`,
          updatedProduct,
          {
            headers: getAuthHeaders()
          }
        );
        
        setProducts(prevProducts =>
          prevProducts.map(product =>
            product.id === updatedProduct.id ? response.data : product
          )
        );
        
        return { success: true, data: response.data };
      }
      
    } catch (err) {
      console.error("Error al actualizar producto:", err);
      
      if (err.response?.status === 401) {
        setError("Tu sesión ha expirado. Por favor, inicia sesión nuevamente.");
      } else {
        setError("Error al actualizar producto: " + err.message);
      }
      
      return { success: false, error: err.message };
    } finally {
      setLoading(false);
    }
  };

  const deleteProduct = async (productId) => {
    setLoading(true);
    try {
      await axios.delete(`${API_BASE_URL}/productos/${productId}`, {
        headers: getAuthHeaders()
      });
      
      setProducts(prevProducts =>
        prevProducts.filter(product => product.id !== productId)
      );
      
      return { success: true };
    } catch (err) {
      console.error("Error al eliminar producto:", err);
      
      if (err.response?.status === 401) {
        setError("Tu sesión ha expirado. Por favor, inicia sesión nuevamente.");
      } else {
        setError("Error al eliminar producto");
      }
      
      return { success: false, error: err.message };
    } finally {
      setLoading(false);
    }
  };

  const getProductsByOwner = (ownerId) => {
    if (!Array.isArray(products)) {
      return [];
    }
    return products.filter(product => product.ownerId === ownerId);
  };

  const filterProducts = (searchTerm = "", stockFilter = "all", category = "all") => {
    if (!Array.isArray(products) || products.length === 0) {
      return [];
    }
    
    return products.filter(product => {
      const matchesSearch = product.title
        ?.toLowerCase()
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
    if (!Array.isArray(productsToSort)) {
      return [];
    }
    
    return [...productsToSort].sort((a, b) => {
      switch (sortBy) {
        case "titleAsc":
          return a.title?.localeCompare(b.title) || 0;
        case "titleDesc":
          return b.title?.localeCompare(a.title) || 0;
        case "priceLow":
          return parseFloat(a.price || 0) - parseFloat(b.price || 0);
        case "priceHigh":
          return parseFloat(b.price || 0) - parseFloat(a.price || 0);
        default:
          return a.title?.localeCompare(b.title) || 0;
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
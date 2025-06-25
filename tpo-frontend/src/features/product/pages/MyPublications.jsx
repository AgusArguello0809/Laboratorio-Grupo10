import { useState, useEffect, useRef } from "react";
import {
  Box,
  Typography,
  Grid,
  Snackbar,
  Alert,
  CircularProgress
} from "@mui/material";
import { useNavigate } from "react-router-dom";

import MyPublicationCard from "../components/management/MyPublicationCard";
import EmptyProductCard from "../components/management/EmptyProductCard";
import FloatingPublishButton from "../components/management/FloatingPublishButton";
import { useAuth } from "../../auth/context/AuthContext";
import { useProductService } from "../hooks/useProductService";
import { useLocation } from "react-router-dom";

export default function MyPublications() {
  console.log("🔥 MyPublications renderizado");
  const hasFetched = useRef(false);
  const location = useLocation();
  const successMessage = location.state?.successMessage || null;
  useEffect(() => {
    if (successMessage) {
      // Limpiar el state para evitar que reaparezca en futuros renders
      window.history.replaceState({}, document.title);
    }
  }, []);
  const { isInitialized, user } = useAuth();
  const {
    loading,
    fetchProducts,
    updateProduct,
    deleteProduct,
    getProductsByOwner
  } = useProductService();

  useEffect(() => {
    if (isInitialized && !hasFetched.current) {
      fetchProducts();
      hasFetched.current = true;
    }
  }, [isInitialized]);

  const [snackbar, setSnackbar] = useState({
    open: !!successMessage,
    message: successMessage || "",
    severity: "success"
  });
  const navigate = useNavigate();

  const handleEdit = async (updatedProduct) => {
    try {
      const result = await updateProduct(updatedProduct);
      if (result.success) {
        setSnackbar({
          open: true,
          message: "Producto actualizado",
          severity: "success"
        });
      } else {
        setSnackbar({
          open: true,
          message: "Error al actualizar producto",
          severity: "error"
        });
      }
    } catch (err) {
      console.error("Error al actualizar producto:", err);
      setSnackbar({
        open: true,
        message: "Error al actualizar producto",
        severity: "error"
      });
    }
  };

  const handleDelete = async (productId) => {
    try {
      const result = await deleteProduct(productId);
      if (result.success) {
        setSnackbar({
          open: true,
          message: "Producto eliminado",
          severity: "info"
        });
      } else {
        setSnackbar({
          open: true,
          message: "Error al eliminar producto",
          severity: "error"
        });
      }
    } catch (err) {
      console.error("Error al eliminar producto:", err);
      setSnackbar({
        open: true,
        message: "Error al eliminar producto",
        severity: "error"
      });
    }
  };

  if (loading) {
    return (
      <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", height: "50vh" }}>
        <CircularProgress />
      </Box>
    );
  }

  const misProductos = getProductsByOwner(user?.id);

  return (
    <Box sx={{ p: 4 }}>
      <Typography variant="h5" gutterBottom>
        Mis Publicaciones
      </Typography>

      <Grid container spacing={2}>
        {misProductos.map((product) => (
          <Grid item key={product.id}>
            <MyPublicationCard
              product={product}
              onEdit={handleEdit}
              onDelete={() => handleDelete(product.id)}
            />
          </Grid>
        ))}

        <Grid item>
          <EmptyProductCard onClick={() => navigate("/sell")} />
        </Grid>
      </Grid>

      <FloatingPublishButton onClick={() => navigate("/sell")} />

      <Snackbar
        open={snackbar.open}
        autoHideDuration={3000}
        onClose={() => setSnackbar((prev) => ({ ...prev, open: false }))}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert
          onClose={() => setSnackbar((prev) => ({ ...prev, open: false }))}
          severity={snackbar.severity}
          variant="filled"
        >
          {snackbar.message}
        </Alert>
      </Snackbar>
    </Box>
  );
}
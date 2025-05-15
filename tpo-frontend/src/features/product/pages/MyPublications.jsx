import { useState } from "react";
import {
  Box,
  Typography,
  Grid,
  Snackbar,
  Alert,
  CircularProgress
} from "@mui/material";
import { useNavigate, Navigate } from "react-router-dom";

import MyPublicationCard from "../components/management/MyPublicationCard";
import EmptyProductCard from "../components/management/EmptyProductCard";
import FloatingPublishButton from "../components/management/FloatingPublishButton";
import { useUser } from "../../auth/context/AuthProvider";
import { useProductService } from "../hooks/useProductService";

export default function MyPublications() {
  const { user } = useUser();
  const { 
    loading, 
    updateProduct, 
    deleteProduct, 
    getProductsByOwner 
  } = useProductService();
  
  const [snackbar, setSnackbar] = useState({
    open: false,
    message: "",
    severity: "success"
  });

  const navigate = useNavigate();

  if (!user) {
    return <Navigate to="/login" replace />;
  }

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

  const misProductos = getProductsByOwner(user?.id);

  if (loading) {
    return (
      <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", height: "50vh" }}>
        <CircularProgress />
      </Box>
    );
  }

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
import { useState, useEffect } from "react";
import {
  Box,
  Typography,
  Grid,
  Snackbar,
  Alert
} from "@mui/material";
import { useNavigate, Navigate } from "react-router-dom";
import axios from "axios";

import MyPublicationCard from "../components/management/MyPublicationCard";
import EmptyProductCard from "../components/management/EmptyProductCard";
import FloatingPublishButton from "../components/management/FloatingPublishButton";
import { useUser } from "../../auth/context/AuthProvider";

export default function MyPublications() {
  const { user } = useUser();
  const [products, setProducts] = useState([]);
  const [snackbar, setSnackbar] = useState({
    open: false,
    message: "",
    severity: "success"
  });

  const navigate = useNavigate();

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  useEffect(() => {
    axios.get("http://localhost:3001/products")
      .then((res) => setProducts(res.data))
      .catch((err) => console.error("Error al obtener productos:", err));
  }, []);

  const handleEdit = async (updatedProduct) => {
    try {
      await axios.patch(`http://localhost:3001/products/${updatedProduct.id}`, updatedProduct);
      setProducts((prev) =>
        prev.map((p) => (p.id === updatedProduct.id ? updatedProduct : p))
      );
      setSnackbar({
        open: true,
        message: "Producto actualizado",
        severity: "success"
      });
    } catch (err) {
      console.error("Error al actualizar producto:", err);
    }
  };

  const handleDelete = async (productId) => {
    try {
      await axios.delete(`http://localhost:3001/products/${productId}`);
      setProducts((prev) => prev.filter((p) => p.id !== productId));
      setSnackbar({
        open: true,
        message: "Producto eliminado",
        severity: "info"
      });
    } catch (err) {
      console.error("Error al eliminar producto:", err);
    }
  };

  const misProductos = products.filter((p) => p.ownerId === user?.id);

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
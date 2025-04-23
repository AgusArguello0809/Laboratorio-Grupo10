import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Typography,
  Box,
  Divider,
  Snackbar,
  Alert
} from "@mui/material";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function DraftPreviewDialog({ open, onClose, product }) {
  const [snackbar, setSnackbar] = useState({ open: false, message: "", severity: "success" });
  const navigate = useNavigate();

  const handlePublish = async () => {
    const isValid =
      product.title?.trim() &&
      product.category?.trim() &&
      parseFloat(product.price) > 0 &&
      parseInt(product.stock) >= 0 &&
      product.description?.trim() &&
      product.images?.length > 0;

    if (!isValid) {
      setSnackbar({
        open: true,
        message: "Faltan completar campos obligatorios o cargar una imagen",
        severity: "warning"
      });
      return;
    }

    try {
      const response = await fetch("http://localhost:3001/products", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(product)
      });

      if (!response.ok) throw new Error("Error al publicar el producto");

      setSnackbar({
        open: true,
        message: "¡Producto publicado con éxito!",
        severity: "success"
      });

      setTimeout(() => {
        setSnackbar({ open: false, message: "", severity: "success" });
        navigate("/my-publications");
        onClose();
      }, 1000);
    } catch (error) {
      setSnackbar({
        open: true,
        message: "Error al publicar el producto",
        severity: "error"
      });
    }
  };

  return (
    <>
      <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
        <DialogTitle>Vista previa del producto</DialogTitle>
        <DialogContent dividers>
          <Box
            component="img"
            src={product.images?.[0]}
            alt="preview"
            sx={{
              width: "100%",
              height: 200,
              objectFit: "cover",
              borderRadius: 1,
              mb: 2
            }}
          />

          <Typography variant="h6">{product.title}</Typography>
          <Typography variant="subtitle1" color="text.secondary">
            {product.category} — Stock: {product.stock}
          </Typography>
          <Typography variant="h6" sx={{ my: 1 }}>
            {product.price}
          </Typography>
          <Divider sx={{ my: 1 }} />
          <Typography variant="body1">{product.description}</Typography>
        </DialogContent>

        <DialogActions>
          <Button onClick={onClose} color="primary">
            Volver atrás
          </Button>
          <Button onClick={handlePublish} color="success" variant="contained">
            Publicar
          </Button>
        </DialogActions>
      </Dialog>

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
    </>
  );
}
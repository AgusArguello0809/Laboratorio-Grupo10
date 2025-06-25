import {
  Dialog, DialogTitle, DialogContent, DialogActions,
  Button, Typography, Box, Divider, Snackbar, Alert
} from "@mui/material";
import { useState, useEffect } from "react";
import { IconButton } from "@mui/material";
import { ChevronLeft, ChevronRight } from "@mui/icons-material";

export default function DraftPreviewDialog({ open, onClose, onConfirm, product }) {
  const [snackbar, setSnackbar] = useState({ open: false, message: "", severity: "success" });
  const [currentImage, setCurrentImage] = useState(0);

  useEffect(() => {
    setCurrentImage(0);
  }, [product.images]);

  const handlePublish = () => {
    const isValid =
      product.title?.trim() &&
      parseInt(product.category) >= 0 &&
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

    onConfirm(); // delega la publicación al padre
    onClose();   // cierra el modal luego de confirmar
  };

  return (
    <>
      <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
        <DialogTitle>Vista previa del producto</DialogTitle>
        <DialogContent dividers>
          {product.images?.length > 0 && (
            <Box sx={{ position: "relative", mb: 2 }}>
              <Box
                component="img"
                src={
                  product.images[currentImage]?.file instanceof File
                    ? URL.createObjectURL(product.images[currentImage].file)
                    : product.images[currentImage] instanceof File
                      ? URL.createObjectURL(product.images[currentImage])
                      : typeof product.images[currentImage] === "string"
                        ? product.images[currentImage]
                        : ""
                }
                alt={`preview-${currentImage}`}
                sx={{
                  width: "100%",
                  height: 250,
                  objectFit: "cover",
                  borderRadius: 2,
                  border: "1px solid #ccc",
                }}
              />

              <Box sx={{ display: "flex", justifyContent: "center", mt: 1 }}>
                {product.images?.map((_, idx) => (
                  <Box
                    key={idx}
                    sx={{
                      width: 10,
                      height: 10,
                      borderRadius: "50%",
                      mx: 0.5,
                      backgroundColor: idx === currentImage ? "#FA9500" : "gray",
                      opacity: idx === currentImage ? 1 : 0.4,
                      transition: "all 0.2s"
                    }}
                  />
                ))}
              </Box>

              {/* Botón Izquierdo */}
              {product.images.length > 1 && (
                <IconButton
                  onClick={() =>
                    setCurrentImage((prev) =>
                      prev === 0 ? product.images.length - 1 : prev - 1
                    )
                  }
                  sx={{
                    position: "absolute",
                    top: "50%",
                    left: 8,
                    transform: "translateY(-50%)",
                    backgroundColor: "rgba(255,255,255,0.7)",
                    ":hover": { backgroundColor: "white" },
                  }}
                >
                  <ChevronLeft />
                </IconButton>
              )}

              {/* Botón Derecho */}
              {product.images.length > 1 && (
                <IconButton
                  onClick={() =>
                    setCurrentImage((prev) =>
                      prev === product.images.length - 1 ? 0 : prev + 1
                    )
                  }
                  sx={{
                    position: "absolute",
                    top: "50%",
                    right: 8,
                    transform: "translateY(-50%)",
                    backgroundColor: "rgba(255,255,255,0.7)",
                    ":hover": { backgroundColor: "white" },
                  }}
                >
                  <ChevronRight />
                </IconButton>
              )}
            </Box>

          )}
          <Typography variant="h6">{product.title}</Typography>
          <Typography variant="subtitle1" color="text.secondary">
            {product.category} — Stock: {product.stock}
          </Typography>
          <Typography variant="h6" sx={{ my: 1 }}>
            ${!isNaN(parseFloat(product.price)) ? parseFloat(product.price).toFixed(2) : "0.00"}
          </Typography>
          <Divider sx={{ my: 1 }} />
          <Typography variant="body1">{product.description}</Typography>
        </DialogContent>

        <DialogActions>
          <Button onClick={onClose} color="primary">Volver atrás</Button>
          <Button onClick={handlePublish} color="success" variant="contained">Publicar</Button>
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
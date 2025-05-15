import React, { useState } from "react";
import {
  Card,
  CardContent,
  CardMedia,
  Typography,
  Button,
  Box,
  Snackbar,
  Alert,
  CircularProgress,
} from "@mui/material";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import { useCarrito } from "../../../cart/context/CarritoProvider";

function ProductCard({ product }) {
  const [open, setOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const { carrito, setCarrito } = useCarrito();

  const handleClose = (event, reason) => {
    if (reason === "clickaway") return;
    setOpen(false);
  };

  const agregarAlCarrito = () => {
    if (loading) return;
    setLoading(true);

    const existente = carrito.find((item) => item.id === product.id);
    const stock = Number(product.stock);
    const precio = typeof product.price === "string"
      ? Number(product.price.replace("$", ""))
      : Number(product.price);

    if (existente) {
      if (existente.cantidad < stock) {
        setCarrito(prev =>
          prev.map((item) =>
            item.id === product.id
              ? { ...item, cantidad: item.cantidad + 1 }
              : item
          )
        );
      } else {
        alert("No hay más stock disponible para este producto.");
        setLoading(false);
        return;
      }
    } else {
      const nuevoItem = {
        id: product.id,
        title: product.title,
        price: product.price,
        cantidad: 1,
        images: product.images,
        stock: product.stock,
        ownerId: product.ownerId
      };
      setCarrito(prev => [...prev, nuevoItem]);
    }

    setOpen(true);
    setTimeout(() => setLoading(false), 1000);
  };

  return (
    <>
      <Card
        sx={{
          width: "100%",
          height: "100%",
          display: "flex",
          flexDirection: "column",
          borderRadius: 2,
          boxShadow: 3,
          overflow: "hidden",
          "&:hover": {
            transform: "translateY(-5px)",
            boxShadow: "0 12px 20px rgba(0,0,0,0.1)",
          },
        }}
      >
    {product.images && (
      <CardMedia
        component="img"
        height="140"
        image={Array.isArray(product.images) ? product.images[0] : product.images}
        alt={product.title}
        sx={{ objectFit: "cover" }}
      />
    )}

        <CardContent
          sx={{
            p: 2,
            "&:last-child": { pb: 2 },
            display: "flex",
            flexDirection: "column",
            flexGrow: 1,
            backgroundColor: "#d1d5db",
          }}
        >
          <Box sx={{ flexGrow: 1 }}>
            <Typography variant="h6" component="div" gutterBottom>
              {product.title}
            </Typography>
            <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
              {product.description}
            </Typography>
          </Box>
          <Typography variant="h5" component="div" sx={{ mb: 2 }}>
            ${!isNaN(parseFloat(product.price)) ? parseFloat(product.price).toFixed(2) : "0.00"}
          </Typography>
          <Button
            variant="contained"
            fullWidth
            onClick={agregarAlCarrito}
            disabled={product.stock === 0 || loading}
            startIcon={
              loading ? (
                <CircularProgress size={20} color="inherit" />
              ) : (
                <ShoppingCartIcon />
              )
            }
            sx={{
              py: 1,
              textTransform: "uppercase",
              color: "black",
              backgroundColor: "#FA9500",
            }}
          >
            {product.stock === 0 ? "Sin stock" : "Add to Cart"}
          </Button>
        </CardContent>
      </Card>

      <Snackbar
        open={open}
        autoHideDuration={1500}
        onClose={handleClose}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert
          onClose={handleClose}
          severity="success"
          variant="filled"
          sx={{ width: "100%" }}
        >
          {product.title} agregado al carrito
        </Alert>
      </Snackbar>
    </>
  );
}

export default ProductCard;
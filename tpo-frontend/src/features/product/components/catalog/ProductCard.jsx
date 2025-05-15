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
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { IconButton } from "@mui/material";
import { useCarrito } from "../../../cart/context/CartContext";
import { useAuth } from "../../../auth/context/AuthContext";
import { useNavigate } from "react-router-dom";


function ProductCard({ product }) {
  const [open, setOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const { carrito, setCarrito } = useCarrito();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [currentImage, setCurrentImage] = useState(0);
  const [hovered, setHovered] = useState(false);

  const nextImage = (e) => {
    e.stopPropagation?.();
    setCurrentImage((prev) => (prev + 1) % product.images.length);
  };

  const prevImage = (e) => {
    e.stopPropagation?.();
    setCurrentImage((prev) =>
      prev === 0 ? product.images.length - 1 : prev - 1
    );
  };

  const handleClose = (event, reason) => {
    if (reason === "clickaway") return;
    setOpen(false);
  };

  const agregarAlCarrito = () => {
    if (!user) {
      navigate("/login");
      return;
    }
    if (loading) return;
    setLoading(true);

    const existente = carrito.find((item) => item.id === product.id);
    const stock = Number(product.stock);
    const price = typeof product.price === "string"
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
        onMouseEnter={() => setHovered(true)}
        onMouseLeave={() => setHovered(false)}
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
        <Box position="relative">
          <Box
            sx={{
              position: "relative",
              width: "100%",
              height: 140,
              overflow: "hidden",
            }}
          >
            <CardMedia
              component="img"
              image={product.images?.[currentImage]}
              alt={product.title}
              sx={{
                width: "100%",
                height: "100%",
                objectFit: "cover",
              }}
            />

            {hovered && product.images?.length > 1 && (
              <>
                <IconButton
                  onClick={prevImage}
                  sx={{
                    position: "absolute",
                    top: "50%",
                    left: 8,
                    transform: "translateY(-50%)",
                    opacity: 1,
                    transition: "opacity 1s ease",
                    backgroundColor: "rgba(0,0,0,0.5)",
                    color: "white",
                    zIndex: 1,
                    "&:hover": {
                      backgroundColor: "rgba(0,0,0,0.7)",
                    },
                  }}
                >
                  <ArrowBackIosIcon fontSize="small" />
                </IconButton>

                <IconButton
                  onClick={nextImage}
                  sx={{
                    position: "absolute",
                    top: "50%",
                    right: 8,
                    transform: "translateY(-50%)",
                    opacity: 1,
                    transition: "opacity 1s ease",
                    backgroundColor: "rgba(0,0,0,0.5)",
                    color: "white",
                    zIndex: 1,
                    "&:hover": {
                      backgroundColor: "rgba(0,0,0,0.7)",
                    },
                  }}
                >
                  <ArrowForwardIosIcon fontSize="small" />
                </IconButton>
              </>
            )}
          </Box>
        </Box>

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
            <Box
              sx={{
                display: "flex",
                justifyContent: "space-between",
                mb: 1,
              }}
            >
              <Typography variant="body2" color="text.secondary">
                <strong>{product.category || "Sin categoría"}</strong>
              </Typography>
              <Typography variant="body2" color="text.secondary">
                <strong>Stock:</strong> {product.stock}
              </Typography>
            </Box>
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
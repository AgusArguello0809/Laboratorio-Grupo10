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
import { getCategoryName } from "../../../config/categories";
import { getToken } from "../../../auth/services/authService";

export const API_BASE_URL = "http://localhost:8080/fitstore-api/v1";

function ProductCard({ product }) {
  const [open, setOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const { carrito, setCarrito, fetchCarrito } = useCarrito();
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

  const agregarAlCarrito = async () => {
    if (!user) {
      navigate("/login");
      return;
    }
    if (loading) return;
    setLoading(true);

    try {
      if (product.ownerId === user.id) {
        alert("No podés comprar tu propio producto.");
        return;
      }

      if (product.stock <= 0) {
        alert("Producto sin stock disponible.");
        return;
      }

      const res = await fetch(`${API_BASE_URL}/carritos`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${getToken()}`,
        },
        body: JSON.stringify({
          productId: product.id,
          cant: 1,
        }),
      });

      if (!res.ok) {
        throw new Error("No se pudo agregar el producto al carrito.");
      }

      // Usar el fetchCarrito que valida e hidrata bien los datos
      await new Promise(resolve => setTimeout(resolve, 300)); // delay opcional por si el back es lento
      await fetchCarrito();

      setOpen(true);
    } catch (error) {
      console.error(error);
      alert(error.message || "Error al agregar el producto al carrito.");
    } finally {
      setLoading(false);
    }
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

            {product.images?.length > 1 && (
              <Box
                sx={{
                  position: "absolute",
                  bottom: 8,
                  left: "50%",
                  transform: "translateX(-50%)",
                  display: "flex",
                  gap: "6px"
                }}
              >
                {product.images.map((_, index) => (
                  <Box
                    key={index}
                    sx={{
                      width: 8,
                      height: 8,
                      borderRadius: "50%",
                      backgroundColor: currentImage === index ? "#FA9500" : "gray",
                      opacity: currentImage === index ? 1 : 0.4,
                      transition: "all 0.2s"
                    }}
                  />
                ))}
              </Box>
            )}

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
                <strong>{getCategoryName(product.categoryId || product.category)}</strong>
              </Typography>
              <Typography variant="body2" color="text.secondary">
                <strong>Stock:</strong> {product.stock}
              </Typography>
            </Box>
            <Typography
              variant="body2"
              color="text.secondary"
              sx={{
                mb: 2,
                display: "-webkit-box",
                WebkitLineClamp: 3,
                WebkitBoxOrient: "vertical",
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "normal",
                wordBreak: "break-word",
                minHeight: "3.6em",
              }}
            >
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
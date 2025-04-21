import React from "react";
import { Card, CardContent, Typography, Button } from "@mui/material";

function ProductCard({ product }) {
  const agregarAlCarrito = () => {
    const carrito = JSON.parse(localStorage.getItem("carrito")) || [];

    const existente = carrito.find((item) => item.nombre === product.name);

    if (existente) {
      existente.cantidad += 1;
    } else {
      carrito.push({
        nombre: product.name,
        precio: typeof product.price === "string"
          ? Number(product.price.replace("$", ""))
          : product.price,
        cantidad: 1,
      });
    }

    localStorage.setItem("carrito", JSON.stringify(carrito));
    alert(`${product.name} agregado al carrito`);
  };

  return (
    <Card sx={{ maxWidth: 345, borderRadius: 2, boxShadow: 3 }}>
      <CardContent>
        <Typography variant="h6" component="div">
          {product.name}
        </Typography>
        <Typography
          variant="body2"
          color="text.secondary"
          sx={{ marginBottom: 2 }}
        >
          {product.description}
        </Typography>
        <Typography variant="h5" component="div" sx={{ marginBottom: 2 }}>
          {product.price}
        </Typography>
        <Button
          variant="contained"
          sx={{ marginTop: 2 }}
          fullWidth
          onClick={agregarAlCarrito}
        >
          Add to Cart
        </Button>
      </CardContent>
    </Card>
  );
}

export default ProductCard;

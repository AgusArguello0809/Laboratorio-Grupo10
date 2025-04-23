import React, { useState, useEffect } from "react";
import { Box, Grid, Typography } from "@mui/material";
import ProductCard from "./ProductCard";

const Products = () => {
  const [productos, setProductos] = useState([]);

  useEffect(() => {
    fetch("/mockProducts.json")
      .then((res) => res.json())
      .then((data) => setProductos(data.sort((a, b) => a.nombre.localeCompare(b.nombre))));
  }, []);

  return (
    <Box p={4}>
      <Typography variant="h4" mb={3}>Catálogo de Productos</Typography>
      <Grid container spacing={2}>
        {productos.map((producto) => (
          <Grid item xs={12} sm={6} md={4} key={producto.id}>
            <ProductCard producto={producto} />
          </Grid>
        ))}
      </Grid>
    </Box>
  );
};

export default Products;

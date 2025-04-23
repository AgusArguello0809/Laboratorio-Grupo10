import React from "react";
import { Container, Typography } from "@mui/material";
import ProductCatalog from "../components/ProductCatalog"; // importás el nuevo componente

const Home = () => {
  return (
    <Container sx={{ marginTop: 4 }}>
      <Typography variant="h3" gutterBottom>
        Bienvenido a MyStore 🛍️
      </Typography>
      <Typography variant="h6" gutterBottom>
        Explora nuestro catálogo de productos:
      </Typography>
      <ProductCatalog />
    </Container>
  );
};

export default Home;

import React from "react";
import { Container, Typography } from "@mui/material";
import ProductCatalog from "../components/catalog/ProductCatalog";

const Home = () => {
  return (
    <Container sx={{ marginTop: 4 }}>
      <Container sx={{ paddingBottom: "16px" }}>
        <Typography variant="h3" gutterBottom>
          Bienvenido a FitStore 🛍️
        </Typography>
        <Typography variant="h6" gutterBottom>
          Explora nuestro catálogo de productos:
        </Typography>
      </Container>

      <ProductCatalog />
    </Container>
  );
};

export default Home;

import React from "react";
import ProductCard from "../components/ProductCard";
import { Container, Grid, Typography } from "@mui/material";

function Home() {
  const products = [
    { id: 1, name: "Product 1", price: "$10", description: "A great product" },
    {
      id: 2,
      name: "Product 2",
      price: "$20",
      description: "Another great product",
    },
    {
      id: 3,
      name: "Product 3",
      price: "$30",
      description: "Yet another great product",
    },
    { id: 4, name: "Product 4", price: "$40", description: "The best product" },
    {
      id: 5,
      name: "Product 5",
      price: "$50",
      description: "You will love this",
    },
    { id: 6, name: "Product 6", price: "$60", description: "Don’t miss this" },
  ];

  return (
    <Container sx={{ marginTop: 4 }}>
      <Typography variant="h4" gutterBottom>
        Welcome to MyStore
      </Typography>
      <Grid container spacing={4}>
        {products.map((product) => (
          <Grid item xs={12} sm={6} md={4} lg={3} key={product.id}>
            <ProductCard product={product} />
          </Grid>
        ))}
      </Grid>
    </Container>
  );
}

export default Home;

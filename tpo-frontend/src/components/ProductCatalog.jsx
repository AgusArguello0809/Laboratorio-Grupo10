import React from "react";
import { Grid, Box, Container } from "@mui/material";
import ProductCard from "./ProductCard";
import mockProducts from "../../mocks/mockProducts.json";

const ProductCatalog = () => {
  const productosOrdenados = [...mockProducts].sort((a, b) =>
    a.nombre.localeCompare(b.nombre)
  );
  
  return (
    <Container maxWidth="lg">
      <Box sx={{
        width: "100%", 
        backgroundColor: "#1e1e1e",
        p: { xs: 2, sm: 3 },
      }}>
        <Grid 
            container 
            sx={{ width: "100%", margin: 0}}
        >
          {productosOrdenados.map((producto) => (
            <Grid 
              item 
              key={producto.id}
              sx={{
                p: 1,
                boxSizing: "border-box",
                width: { xs: "100%", sm: "50%", md: "33.333%" }
              }}
            >
              <ProductCard
                product={{
                  name: producto.nombre,
                  price: parseFloat(producto.precio),
                  description: producto.descripcion,
                  stock: producto.stock,
                  image: producto.imagen,
                }}
              />
            </Grid>
          ))}
        </Grid>
      </Box>
    </Container>
  );
};

export default ProductCatalog;
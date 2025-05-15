import React, { useState } from "react";
import { Grid, Box, Container } from "@mui/material";
import ProductCard from "./ProductCard";
import ProductFilter from "./ProductFilter";
import mockProducts from "../../../../../mocks/mockProducts.json";

const ProductCatalog = () => {
  const [filterOptions, setFilterOptions] = useState({
    searchTerm: "",
    stockFilter: "all",
    sortBy: "nameAsc",
  });

  const filteredProducts = [...mockProducts].filter((producto) => {
    const matchesSearch = producto.nombre
      .toLowerCase()
      .includes(filterOptions.searchTerm.toLowerCase());

    let matchesStock = true;
    if (filterOptions.stockFilter === "inStock") {
      matchesStock = producto.stock > 0;
    } else if (filterOptions.stockFilter === "outOfStock") {
      matchesStock = producto.stock === 0;
    }

    return matchesSearch && matchesStock;
  });

  const sortedProducts = filteredProducts.sort((a, b) => {
    switch (filterOptions.sortBy) {
      case "nameAsc":
        return a.nombre.localeCompare(b.nombre);
      case "nameDesc":
        return b.nombre.localeCompare(a.nombre);
      case "priceLow":
        return parseFloat(a.precio) - parseFloat(b.precio);
      case "priceHigh":
        return parseFloat(b.precio) - parseFloat(a.precio);
      default:
        return a.nombre.localeCompare(b.nombre);
    }
  });

  return (
    <Container maxWidth="lg">
      <Box
        sx={{
          width: "100%",
          backgroundColor: "#1e1e1e",
          p: { xs: 2, sm: 3 },
        }}
      >
        <ProductFilter 
          filterOptions={filterOptions} 
          onFilterChange={setFilterOptions} 
        />
        
        <Grid container sx={{ width: "100%", margin: 0 }}>
          {sortedProducts.length > 0 ? (
            sortedProducts.map((producto) => (
              <Grid
                item
                key={producto.id}
                sx={{
                  p: 1,
                  boxSizing: "border-box",
                  width: { xs: "100%", sm: "50%", md: "33.333%" },
                }}
              >
                <ProductCard
                  product={{
                    id: producto.id,
                    name: producto.nombre,
                    price: parseFloat(producto.precio),
                    description: producto.descripcion,
                    stock: producto.stock,
                    image: producto.imagen,
                  }}
                />
              </Grid>
            ))
          ) : (
            <Box
              sx={{
                width: "100%",
                p: 4,
                textAlign: "center",
                color: "white",
              }}
            >
              No se encontraron productos que coincidan con tu búsqueda
            </Box>
          )}
        </Grid>
      </Box>
    </Container>
  );
};

export default ProductCatalog;
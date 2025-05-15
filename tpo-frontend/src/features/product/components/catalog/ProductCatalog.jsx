import React, { useState, useMemo } from "react";
import { Grid, Box, Container, CircularProgress } from "@mui/material";
import ProductCard from "./ProductCard";
import ProductFilter from "./ProductFilter";
import { useProductService } from "../../hooks/useProductService";

const ProductCatalog = () => {
  const [filterOptions, setFilterOptions] = useState({
    searchTerm: "",
    stockFilter: "all",
    sortBy: "titleAsc",
    category: "all"
  });

  const { products, loading, error, filterProducts, sortProducts } = useProductService();

  const sortedProducts = useMemo(() => {
    const filtered = filterProducts(
      filterOptions.searchTerm,
      filterOptions.stockFilter,
      filterOptions.category
    );

    return sortProducts(filtered, filterOptions.sortBy);
  }, [products, filterOptions, filterProducts, sortProducts]);

  if (loading) {
    return (
      <Container maxWidth="lg">
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "50vh",
          }}
        >
          <CircularProgress />
        </Box>
      </Container>
    );
  }

  if (error) {
    return (
      <Container maxWidth="lg">
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "50vh",
            color: "error.main",
          }}
        >
          {error}
        </Box>
      </Container>
    );
  }

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
                    title: producto.title,
                    price: parseFloat(producto.price),
                    category: producto.category,
                    description: producto.description,
                    stock: producto.stock,
                    images: producto.images,
                    ownerId: producto.ownerId
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
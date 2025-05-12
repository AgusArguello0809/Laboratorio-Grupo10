import React from "react";
import {
  Box,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Grid,
  Typography,
} from "@mui/material";

function ProductFilter({ filterOptions, onFilterChange }) {
  const handleFilterChange = (filterName, value) => {
    onFilterChange({ ...filterOptions, [filterName]: value });
  };

  return (
    <Box sx={{ mb: 3, p: 2, borderRadius: 2, backgroundColor: "#d1d5db" }}>
      <Typography variant="h6" sx={{ mb: 2, color: "black" }}>
        Filtrar productos
      </Typography>
      <Grid container spacing={2}>
        <Grid item xs={12} sm={6} md={4}>
          <TextField
            fullWidth
            label="Buscar por nombre"
            variant="outlined"
            value={filterOptions.searchTerm}
            onChange={(e) => handleFilterChange("searchTerm", e.target.value)}
            size="small"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={4}>
          <FormControl fullWidth size="small">
            <InputLabel>Disponibilidad</InputLabel>
            <Select
              value={filterOptions.stockFilter}
              label="Disponibilidad"
              onChange={(e) => handleFilterChange("stockFilter", e.target.value)}
            >
              <MenuItem value="all">Todos</MenuItem>
              <MenuItem value="inStock">En stock</MenuItem>
              <MenuItem value="outOfStock">Sin stock</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} sm={6} md={4}>
          <FormControl fullWidth size="small">
            <InputLabel>Ordenar por</InputLabel>
            <Select
              value={filterOptions.sortBy}
              label="Ordenar por"
              onChange={(e) => handleFilterChange("sortBy", e.target.value)}
            >
              <MenuItem value="nameAsc">Nombre (A-Z)</MenuItem>
              <MenuItem value="nameDesc">Nombre (Z-A)</MenuItem>
              <MenuItem value="priceLow">Precio (menor a mayor)</MenuItem>
              <MenuItem value="priceHigh">Precio (mayor a menor)</MenuItem>
            </Select>
          </FormControl>
        </Grid>
      </Grid>
    </Box>
  );
}

export default ProductFilter;
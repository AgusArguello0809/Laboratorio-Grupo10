import React from "react";
import { Link } from "react-router-dom";
import { AppBar, Toolbar, Typography, Button, Box } from "@mui/material";

function Navbar() {
  return (
    <AppBar position="static" sx={{ width: "100%" }}>
      <Toolbar sx={{ padding: { xs: "8px 16px", md: "16px 32px" } }}>
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          MyStore
        </Typography>
        <Box sx={{ display: "flex", gap: 2 }}>
          <Button color="inherit" component={Link} to="/">
            Home
          </Button>
          <Button color="inherit" component={Link} to="/products">
            Productos
          </Button>
          <Button color="inherit" component={Link} to="/cart">
            Carro
          </Button>
          <Button color="inherit" component={Link} to="/login">
            Iniciar Sesión
          </Button>
          <Button color="inherit" component={Link} to="/Register">
            Registro
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
}

export default Navbar;

import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Box,
  Menu,
  MenuItem,
} from "@mui/material";

function Navbar() {
  const navigate = useNavigate();
  const usuarioLogueado = JSON.parse(localStorage.getItem("usuario"));
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);

  const handleMenuClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    localStorage.removeItem("usuario");
    handleMenuClose();
    navigate("/"); // redirige al home
  };

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

          <>
            <Button color="inherit" onClick={handleMenuClick}>
              Usuarios
            </Button>
            <Menu
              anchorEl={anchorEl}
              open={open}
              onClose={handleMenuClose}
              anchorOrigin={{
                vertical: "bottom",
                horizontal: "right",
              }}
              transformOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
            >
              {usuarioLogueado ? (
                <MenuItem
                  component={Link}
                  to="/login"
                  onClick={handleMenuClose}
                >
                  Iniciar Sesión
                </MenuItem>
              ) : (
                <MenuItem color="inherit" onClick={handleLogout}>
                  Cerrar sesión
                </MenuItem>
              )}
              <MenuItem
                component={Link}
                to="/register"
                onClick={handleMenuClose}
              >
                Registro
              </MenuItem>
            </Menu>
          </>
        </Box>
      </Toolbar>
    </AppBar>
  );
}

export default Navbar;

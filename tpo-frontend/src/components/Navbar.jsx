import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Box,
  Menu,
  MenuItem,
  Snackbar,
  Alert,
  Badge,
} from "@mui/material";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";

function Navbar() {
  const navigate = useNavigate();
  const [usuarioLogueado, setUsuarioLogueado] = useState(
    JSON.parse(localStorage.getItem("usuario"))
  );
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [cantidadCarrito, setCantidadCarrito] = useState(0);

  // Actualiza la cantidad del carrito al cargar
  useEffect(() => {
    const carrito = JSON.parse(localStorage.getItem("carrito")) || [];
    setCantidadCarrito(carrito.length);

    // Opción: escuchar el evento focus por si vuelven desde otra pestaña
    const handleFocus = () => {
      const nuevo = JSON.parse(localStorage.getItem("carrito")) || [];
      setCantidadCarrito(nuevo.length);
    };

    window.addEventListener("focus", handleFocus);
    return () => window.removeEventListener("focus", handleFocus);
  }, []);

  const handleMenuClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    localStorage.removeItem("usuario");
    setUsuarioLogueado(null);
    handleMenuClose();
    setSnackbarOpen(true);
    navigate("/");
  };

  const handleSnackbarClose = () => {
    setSnackbarOpen(false);
  };

  return (
    <>
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
              <Badge badgeContent={cantidadCarrito} color="secondary">
                <ShoppingCartIcon />
              </Badge>
            </Button>

            {!usuarioLogueado ? (
              <Button color="inherit" component={Link} to="/login">
                Iniciar Sesión
              </Button>
            ) : (
              <>
                <Button
                  color="inherit"
                  onClick={handleMenuClick}
                >
                  {usuarioLogueado.nombre}
                </Button>
                <Menu
                  anchorEl={anchorEl}
                  open={open}
                  onClose={handleMenuClose}
                >
                  <MenuItem onClick={() => navigate("/profile")}>
                    Mi Perfil
                  </MenuItem>
                  <MenuItem onClick={handleLogout}>Cerrar sesión</MenuItem>
                </Menu>
              </>
            )}
          </Box>
        </Toolbar>
      </AppBar>

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={3000}
        onClose={handleSnackbarClose}
        anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
      >
        <Alert onClose={handleSnackbarClose} severity="info" sx={{ width: "100%" }}>
          Sesión cerrada correctamente.
        </Alert>
      </Snackbar>
    </>
  );
}

export default Navbar;

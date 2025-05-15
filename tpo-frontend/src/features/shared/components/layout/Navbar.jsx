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
  Snackbar,
  Alert,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  Divider,
  useMediaQuery,
  Badge,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import { useTheme } from "@mui/material/styles";
import { useUser } from "../../../auth/context/AuthProvider";
import { useCarrito } from "../../../cart/context/CarritoProvider";

function Navbar() {
  // Modificamos para usar logout en lugar de setUser
  const { user, logout } = useUser();
  const { cantidadTotal } = useCarrito();
  const navigate = useNavigate();
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("md"));

  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [drawerOpen, setDrawerOpen] = useState(false);

  const handleMenuClick = (event) => setAnchorEl(event.currentTarget);
  const handleMenuClose = () => setAnchorEl(null);

  // Actualizamos para usar la función logout del contexto
  const handleLogout = () => {
    logout(); // Esta función ya se encarga de remover el token y el usuario
    handleMenuClose();
    setSnackbarOpen(true);
    navigate("/");
  };

  const handleSnackbarClose = () => setSnackbarOpen(false);

  const renderNavLinks = () => (
    <>
      <Button color="inherit" component={Link} to="/">
        Home
      </Button>
      <Button color="inherit" component={Link} to="/my-publications">
        Mis Publicaciones
      </Button>
      <Button color="inherit" component={Link} to="/sell">
        Vender
      </Button>
      {!user ? (
        <>
          <Button color="inherit" component={Link} to="/login">
            Iniciar Sesión
          </Button>
          <Button color="inherit" component={Link} to="/register">
            Registrarse
          </Button>
        </>
      ) : (
        <>
          <Button color="inherit" onClick={handleMenuClick}>
            {user.name + " " + user.lastName || "Usuario"}
          </Button>
          <Menu anchorEl={anchorEl} open={open} onClose={handleMenuClose}>
            <MenuItem onClick={handleLogout}>Cerrar sesión</MenuItem>
          </Menu>
        </>
      )}

      <Button color="inherit" component={Link} to="/cart">
        <Badge
          badgeContent={user && cantidadTotal > 0 ? cantidadTotal : null}
          color="error"
          max={99}
          invisible={!user || cantidadTotal === 0}
        >
          <ShoppingCartIcon />
        </Badge>
      </Button>
    </>
  );

  const renderDrawerLinks = () => (
    <Box
      sx={{ width: 250, height: "100%", backgroundColor: "#FA9500" }}
      role="presentation"
      onClick={() => setDrawerOpen(false)}
    >
      <List>
        <ListItem disablePadding>
          <ListItemButton component={Link} to="/">
            <ListItemText primary="Home" />
          </ListItemButton>
        </ListItem>
        <ListItem disablePadding>
          <ListItemButton component={Link} to="/products">
            <ListItemText primary="Productos" />
          </ListItemButton>
        </ListItem>
        <ListItem disablePadding>
          <ListItemButton component={Link} to="/my-publications">
            <ListItemText primary="Mis Publicaciones" />
          </ListItemButton>
        </ListItem>
        <ListItem disablePadding>
          <ListItemButton component={Link} to="/sell">
            <ListItemText primary="Vender" />
          </ListItemButton>
        </ListItem>
        <Divider />
        {!user ? (
          <>
            <ListItem disablePadding>
              <ListItemButton component={Link} to="/login">
                <ListItemText primary="Iniciar Sesión" />
              </ListItemButton>
            </ListItem>
            <ListItem disablePadding>
              <ListItemButton component={Link} to="/register">
                <ListItemText primary="Registrarse" />
              </ListItemButton>
            </ListItem>
          </>
        ) : (
          <ListItem disablePadding>
            <ListItemButton onClick={handleLogout}>
              <ListItemText primary="Cerrar sesión" />
            </ListItemButton>
          </ListItem>
        )}
        <ListItem disablePadding>
          <ListItemButton component={Link} to="/cart">
            <ListItemText primary="Carrito" />
            <Badge
              badgeContent={user && cantidadTotal > 0 ? cantidadTotal : null}
              color="error"
              max={99}
              invisible={!user || cantidadTotal === 0}
            >
              <ShoppingCartIcon />
            </Badge>
          </ListItemButton>
        </ListItem>
      </List>
    </Box>
  );

  return (
    <>
      <AppBar
        position="static"
        sx={{ width: "100%", backgroundColor: "#FA9500", color: "black" }}
      >
        <Toolbar sx={{ padding: { xs: "8px 16px", md: "16px 32px" } }}>
          <Typography variant="h5" sx={{ flexGrow: 1, fontWeight: 700 }}>
            FitStore
          </Typography>
          {isMobile ? (
            <>
              <IconButton
                edge="end"
                color="inherit"
                onClick={() => setDrawerOpen(true)}
              >
                <MenuIcon />
              </IconButton>
              <Drawer
                anchor="right"
                open={drawerOpen}
                onClose={() => setDrawerOpen(false)}
              >
                {renderDrawerLinks()}
              </Drawer>
            </>
          ) : (
            <Box sx={{ display: "flex", gap: 2 }}>{renderNavLinks()}</Box>
          )}
        </Toolbar>
      </AppBar>

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={3000}
        onClose={handleSnackbarClose}
        anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
      >
        <Alert
          onClose={handleSnackbarClose}
          severity="info"
          sx={{ width: "100%" }}
        >
          Sesión cerrada correctamente.
        </Alert>
      </Snackbar>
    </>
  );
}

export default Navbar;
import React, { useState } from 'react';
import {
  Box,
  Card,
  CardContent,
  CardActions,
  Button,
  Typography,
  Divider,
  TextField,
  Snackbar,
  Alert,
  IconButton,
} from '@mui/material';
import { Check } from '@mui/icons-material';
import { useCarrito } from '../context/CartContext';
import { useAuth } from '../../auth/context/AuthContext';
import { getToken } from '../../auth/services/authService';


const API_BASE_URL = "http://localhost:8080/fitstore-api/v1";

const Carrito = () => {
  const { carrito, setCarrito, fetchCarrito } = useCarrito();
  const { user } = useAuth();
  const carritoId = carrito?.id;
  const token = getToken();

  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'info' });
  const [cantidadTemp, setCantidadTemp] = useState({});

  const headers = {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  };

  const disminuirCantidad = async (id) => {
    const producto = carrito.productos.find(p => p.productoId === id);
    if (!producto || producto.cantidad <= 1) {
      setSnackbar({
        open: true,
        message: 'No podés tener menos de 1 unidad.',
        severity: 'warning',
      });
      return;
    }

    try {
      await fetch(`${API_BASE_URL}/carritos/${carrito.id}/producto/${id}/disminuir`, {
        method: 'PATCH',
        headers
      });
      await fetchCarrito();
    } catch (e) {
      setSnackbar({ open: true, message: 'Error al disminuir cantidad', severity: 'error' });
    }
  };

  const aumentarCantidad = async (id) => {
    try {
      await fetch(`${API_BASE_URL}/carritos/${carritoId}/producto/${id}/incrementar`, {
        method: 'PATCH',
        headers
      });
      await fetchCarrito();
    } catch (e) {
      setSnackbar({ open: true, message: 'Error al aumentar cantidad', severity: 'error' });
    }
  };

  const manejarInputTemp = (id, valor) => {
    setCantidadTemp((prev) => ({
      ...prev,
      [id]: valor,
    }));
  };

  const confirmarCantidad = async (id) => {
    const valor = cantidadTemp[id];
    if (valor === undefined || valor === '') return;

    const num = parseInt(valor, 10);
    if (isNaN(num) || num < 1 || num > 9999) {
      setSnackbar({
        open: true,
        message: 'La cantidad debe estar entre 1 y 9999.',
        severity: 'warning',
      });
      return;
    }

    const producto = carrito.productos.find(p => p.id === id || p.productoId === id);
    if (producto && num > producto.stock) {
      setSnackbar({
        open: true,
        message: `No hay suficiente stock para ${producto.title}.`,
        severity: 'error',
      });
      return;
    }

    try {
      await fetch(`${API_BASE_URL}/carritos/${carritoId}/producto/${id}/cantidad`, {
        method: 'PATCH',
        headers,
        body: JSON.stringify({ cantidad: num }),
      });
      await fetchCarrito();
      setCantidadTemp((prev) => {
        const nuevo = { ...prev };
        delete nuevo[id];
        return nuevo;
      });
    } catch (e) {
      setSnackbar({
        open: true,
        message: 'Error al actualizar cantidad',
        severity: 'error',
      });
    }
  };

  const eliminarProducto = async (id) => {
    try {
      await fetch(`${API_BASE_URL}/carritos/${carritoId}/producto/${id}`, {
        method: 'DELETE',
        headers,
      });
      await fetchCarrito();
    } catch (e) {
      setSnackbar({ open: true, message: 'Error al eliminar producto', severity: 'error' });
    }
  };

  const vaciarCarrito = async () => {
    try {
      await fetch(`${API_BASE_URL}/carritos/${carritoId}/vaciar`, {
        method: 'DELETE',
        headers
      });
      await fetchCarrito();
    } catch (e) {
      setSnackbar({ open: true, message: 'Error al vaciar carrito', severity: 'error' });
    }
  };

  const handleCheckout = async () => {
    try {
      const res = await fetch(`${API_BASE_URL}/carritos/${carritoId}/checkout`, {
        method: 'POST',
        headers
      });

      if (!res.ok) {
        throw new Error("Error al procesar el checkout");
      }

      await fetchCarrito();
      setSnackbar({ open: true, message: '¡Gracias por tu compra!', severity: 'success' });
    } catch (e) {
      setSnackbar({ open: true, message: e.message || 'Error al hacer checkout', severity: 'error' });
    }
  };

  const total = carrito.productos.reduce(
    (acc, item) => {
      const precio = parseFloat(item.price);
      const cantidad = Number(item.cantidad || 0);
      return acc + (isNaN(precio) ? 0 : precio * cantidad);
    },
    0
  );
  return (
    <Box sx={{ padding: 4 }}>
      <Typography variant="h4" gutterBottom>
        Carrito de Compras
      </Typography>

      {carrito.productos.length === 0 ? (
        <Typography variant="body1">El carrito está vacío.</Typography>
      ) : (
        <>
          {carrito.productos.map((item) => (
            <Card key={item.productoId} sx={{ mb: 2 }}>
              <CardContent>
                <Typography variant="h6">{item.title}</Typography>
                <Typography>
                  Precio unitario: ${isNaN(parseFloat(item.price)) ? "N/A" : parseFloat(item.price).toFixed(2)} | Stock: {item.stock}
                </Typography>
                <Typography variant="subtitle2" sx={{ mb: 1 }}>
                  Subtotal: ${(
                    (isNaN(parseFloat(item.price)) ? 0 : parseFloat(item.price)) *
                    (Number(item.cantidad || 0))
                  ).toFixed(2)}
                </Typography>
                <CardActions sx={{ gap: 1 }}>
                  <Button
                    size="small"
                    variant="outlined"
                    onClick={() => disminuirCantidad(item.productoId)}
                    sx={estiloBoton('-')}
                  >
                    -
                  </Button>

                  <TextField
                    size="small"
                    type="text"
                    value={cantidadTemp[item.productoId] ?? item.cantidad}
                    onChange={(e) => manejarInputTemp(item.productoId, e.target.value)}
                    sx={{ width: 90 }}
                    inputProps={{
                      style: { textAlign: 'center' },
                      inputMode: 'numeric',
                      pattern: '[0-9]*',
                      min: 1,
                      max: 9999,
                    }}
                  />

                  <IconButton
                    color="primary"
                    onClick={() => confirmarCantidad(item.productoId)}
                    disabled={
                      cantidadTemp[item.productoId] === undefined ||
                      cantidadTemp[item.productoId] === '' ||
                      Number(cantidadTemp[item.productoId]) === item.cantidad
                    }
                  >
                    <Check />
                  </IconButton>

                  <Button
                    size="small"
                    variant="outlined"
                    onClick={() => aumentarCantidad(item.productoId)}
                    sx={estiloBoton('+')}
                  >
                    +
                  </Button>

                  <Button
                    size="small"
                    color="error"
                    onClick={() => eliminarProducto(item.productoId)}
                  >
                    Eliminar
                  </Button>
                </CardActions>
              </CardContent>
            </Card>
          ))}

          <Divider sx={{ my: 2 }} />
          <Typography variant="h6">Total: ${total.toFixed(2)}</Typography>

          <Box sx={{ mt: 2, display: 'flex', gap: 2 }}>
            <Button variant="outlined" color="error" onClick={vaciarCarrito}>
              Vaciar carrito
            </Button>
            <Button variant="contained" color="success" onClick={handleCheckout}>
              Checkout
            </Button>
          </Box>
        </>
      )}

      <Snackbar
        open={snackbar.open}
        autoHideDuration={3000}
        onClose={() => setSnackbar({ ...snackbar, open: false })}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert
          onClose={() => setSnackbar({ ...snackbar, open: false })}
          severity={snackbar.severity}
          sx={{ width: '100%' }}
        >
          {snackbar.message}
        </Alert>
      </Snackbar>
    </Box>
  );
};

// Estilos para botones de cantidad
const estiloBoton = (tipo) => ({
  minWidth: 36,
  borderRadius: 1,
  fontWeight: 'bold',
  ...(tipo === '-' && {
    color: '#1976d2',
    borderColor: '#1976d2',
    '&:hover': {
      backgroundColor: '#e3f2fd',
      borderColor: '#1565c0',
      color: '#1565c0',
    },
  }),
  ...(tipo === '+' && {
    color: '#388e3c',
    borderColor: '#388e3c',
    '&:hover': {
      backgroundColor: '#e8f5e9',
      borderColor: '#2e7d32',
      color: '#2e7d32',
    },
  }),
});

export default Carrito;
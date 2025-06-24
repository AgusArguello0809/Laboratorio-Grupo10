import React, {useState } from 'react';
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
import { useProductService } from '../../product/hooks/useProductService';
import { getToken } from '../../auth/services/authService';

const API_BASE_URL = "http://localhost:8080/fitstore-api/v1";

const Carrito = () => {
  const { carrito, setCarrito } = useCarrito();
  const { user } = useAuth();
  const { fetchProducts } = useProductService();
  const token = getToken();

  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'info' });

  // Guarda inputs temporales antes de confirmar
  const [cantidadTemp, setCantidadTemp] = useState({});

  const disminuirCantidad = (id) => {
    setCarrito((prev) =>
      prev.map((item) =>
        item.id === id && item.cantidad > 1
          ? { ...item, cantidad: item.cantidad - 1 }
          : item
      )
    );
  };

  const aumentarCantidad = (id) => {
    setCarrito((prev) =>
      prev.map((item) =>
        item.id === id
          ? { ...item, cantidad: Math.min(item.cantidad + 1, item.stock) }
          : item
      )
    );
  };

  // Al escribir en el input, guarda temporalmente
  const manejarInputTemp = (id, valor) => {
    setCantidadTemp((prev) => ({
      ...prev,
      [id]: valor,
    }));
  };

  // Confirmar el valor ingresado al presionar el check
  const confirmarCantidad = (id) => {
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

    const producto = carrito.find(p => p.id === id);
    if (producto && num > producto.stock) {
      setSnackbar({
        open: true,
        message: `No hay suficiente stock para ${producto.title}.`,
        severity: 'error',
      });
      return;
    }

    setCarrito((prev) =>
      prev.map((item) =>
        item.id === id ? { ...item, cantidad: num } : item
      )
    );

    setCantidadTemp((prev) => {
      const nuevo = { ...prev };
      delete nuevo[id];
      return nuevo;
    });
  };

  const eliminarProducto = (id) => {
    setCarrito((prev) => prev.filter((item) => item.id !== id));
    setCantidadTemp((prev) => {
      const nuevo = { ...prev };
      delete nuevo[id];
      return nuevo;
    });
  };

  const vaciarCarrito = () => {
    setCarrito([]);
    setCantidadTemp({});
  };

  const handleCheckout = async () => {
    const itemsValidos = carrito.filter(
      item =>
        Number(item.cantidad) > 0 &&
        !isNaN(item.cantidad) &&
        Number(item.price) > 0
    );

    if (itemsValidos.length === 0) {
      setSnackbar({
        open: true,
        message: 'Debés agregar al menos un producto válido para completar la compra.',
        severity: 'warning',
      });
      return;
    }

    const totalCompra = itemsValidos.reduce(
      (acc, item) => acc + (Number(item.price) * Number(item.cantidad)),
      0
    );

    if (totalCompra === 0) {
      setSnackbar({
        open: true,
        message: 'El total de la compra no puede ser cero.',
        severity: 'warning',
      });
      return;
    }

    try {
      const productosPropios = itemsValidos.filter(item => item.ownerId === user?.id);
      if (productosPropios.length > 0) {
        setSnackbar({
          open: true,
          message: 'No podés comprar tus propios productos.',
          severity: 'warning',
        });
        return;
      }

      const erroresStock = itemsValidos.find(item => item.stock < item.cantidad);
      if (erroresStock) {
        setSnackbar({
          open: true,
          message: `No hay suficiente stock de ${erroresStock.title}.`,
          severity: 'error',
        });
        return;
      }

      // 🚀 Usar backend Java para actualizar stock
      await Promise.all(
        itemsValidos.map(async (item) => {
          const res = await fetch(`${API_BASE_URL}/productos/${item.id}`, {
            headers: {
              'Authorization': `Bearer ${token}`,
            },
          });
          
          if (!res.ok) {
            throw new Error(`Error al obtener producto ${item.title}`);
          }
          
          const productoActual = await res.json();
          const nuevoStock = productoActual.stock - item.cantidad;

          if (nuevoStock < 0) {
            throw new Error(`No hay suficiente stock de ${item.title}`);
          }

          // Actualizar stock en backend Java
          const updateRes = await fetch(`${API_BASE_URL}/productos/${item.id}`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`,
            },
            body: JSON.stringify({
              ...productoActual,
              stock: nuevoStock
            }),
          });

          if (!updateRes.ok) {
            throw new Error(`Error al actualizar stock de ${item.title}`);
          }
        })
      );

      await fetchProducts();
      setSnackbar({ open: true, message: '¡Gracias por tu compra!', severity: 'success' });
      vaciarCarrito();
    } catch (error) {
      console.error('Error al actualizar el stock:', error);
      setSnackbar({
        open: true,
        message: error.message || 'Hubo un error al procesar el pedido.',
        severity: 'error',
      });
    }
  };

  const total = carrito.reduce(
    (acc, item) => acc + (Number(item.price) * Number(item.cantidad || 0)),
    0
  );

  return (
    <Box sx={{ padding: 4 }}>
      <Typography variant="h4" gutterBottom>
        Carrito de Compras
      </Typography>

      {carrito.length === 0 ? (
        <Typography variant="body1">El carrito está vacío.</Typography>
      ) : (
        <>
          {carrito.map((item) => (
            <Card key={item.id} sx={{ mb: 2 }}>
              <CardContent>
                <Typography variant="h6">{item.title}</Typography>
                <Typography>
                  Precio unitario: ${item.price} | Stock: {item.stock}
                </Typography>
                <Typography variant="subtitle2" sx={{ mb: 1 }}>
                  Subtotal: ${(item.price * (item.cantidad || 0)).toFixed(2)}
                </Typography>
                <CardActions sx={{ gap: 1 }}>
                  <Button
                    size="small"
                    variant="outlined"
                    onClick={() => disminuirCantidad(item.id)}
                    sx={estiloBoton('-')}
                  >
                    -
                  </Button>

                  <TextField
                    size="small"
                    type="text"
                    value={cantidadTemp[item.id] ?? item.cantidad}
                    onChange={(e) => manejarInputTemp(item.id, e.target.value)}
                    sx={{ width: 90 }}
                    inputMode={{
                      style: { textAlign: 'center' },
                      inputMode: 'numeric',
                      pattern: '[0-9]*',
                      min: 1,
                      max: 9999,
                    }}
                  />

                  <IconButton
                    color="primary"
                    onClick={() => confirmarCantidad(item.id)}
                    disabled={
                      cantidadTemp[item.id] === undefined ||
                      cantidadTemp[item.id] === '' ||
                      Number(cantidadTemp[item.id]) === item.cantidad
                    }
                  >
                    <Check />
                  </IconButton>

                  <Button
                    size="small"
                    variant="outlined"
                    onClick={() => aumentarCantidad(item.id)}
                    sx={estiloBoton('+')}
                  >
                    +
                  </Button>

                  <Button
                    size="small"
                    color="error"
                    onClick={() => eliminarProducto(item.id)}
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
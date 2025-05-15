import React from 'react';
import { useEffect } from 'react';
import { useNavigate, Navigate } from "react-router-dom";
import {
  Box,
  Card,
  CardContent,
  CardActions,
  Button,
  Typography,
  Divider,
} from '@mui/material';
import { useCarrito } from '../context/CarritoProvider';
import { useUser } from '../../auth/context/AuthProvider'

const Carrito = () => {
  const { carrito, setCarrito } = useCarrito();
  const { user } = useUser();
  const navigate = useNavigate();

  useEffect(() => {
  if (!user) {
    navigate("/login");
  }
  }, [user]);

  const eliminarProducto = (id) => {
    setCarrito(prev => prev.filter((item) => item.id !== id));
  };

const aumentarCantidad = (id) => {
  setCarrito(prev =>
    prev.map(item =>
      item.id === id ? { ...item, cantidad: item.cantidad + 1 } : item
    )
  );
};

const disminuirCantidad = (id) => {
  setCarrito(prev =>
    prev.map(item =>
      item.id === id && item.cantidad > 1
        ? { ...item, cantidad: item.cantidad - 1 }
        : item
    )
  );
};

  const vaciarCarrito = () => {
    setCarrito([]);
  };

  const handleCheckout = async () => {
    try {
      for (const item of carrito) {
        const nuevoStock = item.stock - item.cantidad;

        if (nuevoStock < 0) {
          alert(`No hay suficiente stock de ${item.nombre}`);
          return;
        }

        await fetch(`http://localhost:3001/products/${item.id}`, {
          method: 'PATCH',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ stock: nuevoStock }),
        });
      }

      alert('¡Gracias por tu compra!');
      vaciarCarrito();
    } catch (error) {
      console.error('Error al actualizar el stock:', error);
      alert('Hubo un error al procesar el pedido.');
    }
  };

  const total = carrito.reduce(
    (acc, item) => acc + item.precio * item.cantidad,
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
          {carrito.map((item, index) => (
            <Card key={item.id} sx={{ mb: 2 }}>
              <CardContent>
                <Typography variant="h6">{item.nombre}</Typography>
                <Typography>
                  Cantidad: {item.cantidad} | Precio unitario: ${item.precio}
                </Typography>
                <Typography variant="subtitle2">
                  Subtotal: ${(item.precio * item.cantidad).toFixed(2)}
                </Typography>
              </CardContent>
              <CardActions>
                <Button
                  size="small"
                  variant="outlined"
                  sx={{
                    minWidth: 36,
                    borderRadius: 1,
                    color: '#1976d2',
                    borderColor: '#1976d2',
                    fontWeight: 'bold',
                    '&:hover': {
                      backgroundColor: '#e3f2fd',
                      borderColor: '#1565c0',
                      color: '#1565c0',
                    },
                  }}
                  onClick={() => disminuirCantidad(item.id)}
                >
                  -
                </Button>
                <Button
                  size="small"
                  variant="outlined"
                  sx={{
                    minWidth: 36,
                    borderRadius: 1,
                    color: '#388e3c',
                    borderColor: '#388e3c',
                    fontWeight: 'bold',
                    '&:hover': {
                      backgroundColor: '#e8f5e9',
                      borderColor: '#2e7d32',
                      color: '#2e7d32',
                    },
                  }}
                  onClick={() => aumentarCantidad(item.id)}
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
            </Card>
          ))}

          <Divider sx={{ my: 2 }} />

          <Typography variant="h6">
            Total: ${total.toFixed(2)}
          </Typography>

          <Box sx={{ mt: 2, display: 'flex', gap: 2 }}>
            <Button variant="outlined" color="error" onClick={vaciarCarrito}>
              Vaciar carrito
            </Button>
            <Button
              variant="contained"
              color="success"
              onClick={handleCheckout}
            >
              Checkout
            </Button>
          </Box>
        </>
      )}
    </Box>
  );
};

export default Carrito;
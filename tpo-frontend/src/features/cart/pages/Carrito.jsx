import React, { useEffect, useState } from 'react';
import {
  Box,
  Card,
  CardContent,
  CardActions,
  Button,
  Typography,
  Divider,
} from '@mui/material';

const Carrito = () => {
  const [carrito, setCarrito] = useState([]);

  const cargarCarrito = () => {
    const carritoGuardado = JSON.parse(localStorage.getItem('carrito')) || [];
    setCarrito(carritoGuardado);
  };

  useEffect(() => {
    cargarCarrito();
    window.addEventListener('focus', cargarCarrito);
    return () => {
      window.removeEventListener('focus', cargarCarrito);
    };
  }, []);

  const guardarCarrito = (nuevoCarrito) => {
    setCarrito(nuevoCarrito);
    localStorage.setItem('carrito', JSON.stringify(nuevoCarrito));
  };

  const eliminarProducto = (nombre) => {
    const nuevoCarrito = carrito.filter((item) => item.nombre !== nombre);
    guardarCarrito(nuevoCarrito);
  };

  const aumentarCantidad = (index) => {
    const nuevoCarrito = [...carrito];
    nuevoCarrito[index].cantidad += 1;
    guardarCarrito(nuevoCarrito);
  };

  const disminuirCantidad = (index) => {
    const nuevoCarrito = [...carrito];
    if (nuevoCarrito[index].cantidad > 1) {
      nuevoCarrito[index].cantidad -= 1;
      guardarCarrito(nuevoCarrito);
    }
  };

  const vaciarCarrito = () => {
    guardarCarrito([]);
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
            <Card key={index} sx={{ mb: 2 }}>
              <CardContent>
                <Typography variant="h6">{item.nombre}</Typography>
                <Typography>
                  Cantidad: {item.cantidad} | Precio unitario: ${item.precio}
                </Typography>
                <Typography variant="subtitle2">
                  Subtotal: ${item.precio * item.cantidad}
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
                  onClick={() => disminuirCantidad(index)}
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
                  onClick={() => aumentarCantidad(index)}
                >
                  +
                </Button>
                <Button
                  size="small"
                  color="error"
                  onClick={() => eliminarProducto(item.nombre)}
                >
                  Eliminar
                </Button>
              </CardActions>
            </Card>
          ))}

          <Divider sx={{ my: 2 }} />

          <Typography variant="h6">Total: ${total}</Typography>

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

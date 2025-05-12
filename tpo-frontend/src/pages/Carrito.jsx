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

    // Escuchamos cuando el usuario vuelve a la pestaña
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
                <Button size="small" onClick={() => disminuirCantidad(index)}>
                  -
                </Button>
                <Button size="small" onClick={() => aumentarCantidad(index)}>
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
            <Button variant="outlined" onClick={vaciarCarrito}>
              Vaciar carrito
            </Button>
            <Button
              variant="contained"
              color="primary"
              onClick={() => {
                alert('¡Gracias por tu compra!');
                vaciarCarrito();
              }}
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

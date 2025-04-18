import React, { useState } from "react";
import { TextField, Button, Box, Typography, Container } from "@mui/material";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();

    // Aquí validamos los datos ingresados
    if (email === "" || password === "") {
      setError("Please fill in both fields.");
      return;
    }

    // Si todo es correcto, puedes agregar lógica para autenticar al usuario
    setError("");
    console.log("Logging in with:", email, password);
    // Redirigir o hacer alguna acción después de login...
  };

  return (
    <Container maxWidth="xs">
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          marginTop: 4,
          padding: 3,
          borderRadius: 2,
          boxShadow: 3,
        }}
      >
        <Typography variant="h5" gutterBottom>
          Login
        </Typography>
        {error && (
          <Typography variant="body2" color="error" sx={{ marginBottom: 2 }}>
            {error}
          </Typography>
        )}
        <form onSubmit={handleSubmit} style={{ width: "100%" }}>
          <TextField
            label="Email"
            type="email"
            fullWidth
            variant="outlined"
            sx={{ marginBottom: 2 }}
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <TextField
            label="Password"
            type="password"
            fullWidth
            variant="outlined"
            sx={{ marginBottom: 2 }}
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            sx={{ marginTop: 2 }}
          >
            Log In
          </Button>
        </form>
      </Box>
    </Container>
  );
}

export default Login;

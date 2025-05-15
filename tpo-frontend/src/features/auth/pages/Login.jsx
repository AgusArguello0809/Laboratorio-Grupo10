import React, { useState } from "react";
import {
  TextField,
  Button,
  Box,
  Typography,
  Container,
  Avatar,
  Paper,
  Snackbar,
  Alert,
} from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { Formik, Form } from "formik";
import * as Yup from "yup";
import { useNavigate, useLocation } from "react-router-dom";
import { useUser } from "../context/AuthProvider";

const validationSchema = Yup.object({
  identificador: Yup.string().required("El email o usuario es obligatorio"),
  password: Yup.string()
    .min(6, "La contraseña debe tener al menos 6 caracteres")
    .required("La contraseña es obligatoria"),
});

function Login() {
  const { login, loading, error: authError } = useUser();
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState("info");
  const navigate = useNavigate();
  const location = useLocation();

  // Redirigir a la página anterior o a la página principal
  const from = location.state?.from?.pathname || "/";

  const handleSubmit = async (values, { setSubmitting }) => {
    try {
      // Usamos el email como identificador para JWT
      // La API de JWT normalmente espera un email específico
      const result = await login(values.identificador, values.password);
      
      if (result.success) {
        setSnackbarMessage("✅ ¡Entraste!");
        setSnackbarSeverity("success");
        setOpenSnackbar(true);
        
        setTimeout(() => {
          navigate(from, { replace: true });
        }, 1500);
      } else {
        // Si hay error específico del login
        setSnackbarMessage(`❌ ${result.error || "Usuario o contraseña incorrectos"}`);
        setSnackbarSeverity("error");
        setOpenSnackbar(true);
      }
    } catch (error) {
      console.error("Error al hacer login:", error);
      setSnackbarMessage(`❌ ${error.message || "Error de conexión"}`);
      setSnackbarSeverity("error");
      setOpenSnackbar(true);
    }
    
    setSubmitting(false);
  };

  // Mostrar error de autenticación del contexto si existe
  React.useEffect(() => {
    if (authError) {
      setSnackbarMessage(`❌ ${authError}`);
      setSnackbarSeverity("error");
      setOpenSnackbar(true);
    }
  }, [authError]);

  return (
    <Container maxWidth="xs">
      <Paper
        elevation={4}
        sx={{
          padding: 4,
          borderRadius: 3,
          marginTop: 8,
          background: "#fff",
        }}
      >
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, backgroundColor: "#FA9500", color: "black" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Iniciar Sesión
          </Typography>
        </Box>

        <Formik
          initialValues={{ identificador: "", password: "" }}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
        >
          {({
            values,
            errors,
            touched,
            handleChange,
            handleBlur,
            isSubmitting,
          }) => (
            <Form>
              <TextField
                margin="normal"
                fullWidth
                label="Email o nombre de usuario"
                name="identificador"
                type="text"
                autoComplete="username"
                value={values.identificador}
                onChange={handleChange}
                onBlur={handleBlur}
                error={touched.identificador && Boolean(errors.identificador)}
                helperText={touched.identificador && errors.identificador}
              />
              <TextField
                margin="normal"
                fullWidth
                label="Contraseña"
                name="password"
                type="password"
                autoComplete="current-password"
                value={values.password}
                onChange={handleChange}
                onBlur={handleBlur}
                error={touched.password && Boolean(errors.password)}
                helperText={touched.password && errors.password}
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                disabled={isSubmitting || loading}
                sx={{
                  marginTop: 2,
                  backgroundColor: "#FA9500",
                  color: "black",
                }}
              >
                {loading ? "Iniciando sesión..." : "Iniciar sesión"}
              </Button>
            </Form>
          )}
        </Formik>
      </Paper>

      <Snackbar
        open={openSnackbar}
        autoHideDuration={3000}
        onClose={() => setOpenSnackbar(false)}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert
          onClose={() => setOpenSnackbar(false)}
          severity={snackbarSeverity}
          sx={{ width: "100%" }}
        >
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </Container>
  );
}

export default Login;
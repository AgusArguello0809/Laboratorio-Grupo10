import React, { useState } from "react";
import {
  TextField,
  Button,
  Box,
  Typography,
  Container,
  Avatar,
  Paper,
  Grid,
  Snackbar,
  Alert,
} from "@mui/material";
import PersonAddAltIcon from "@mui/icons-material/PersonAddAlt";
import { Formik, Form } from "formik";
import { useNavigate } from "react-router-dom";
import * as Yup from "yup";

const validationSchema = Yup.object({
  username: Yup.string()
    .min(8, "Debe tener al menos 8 caracteres")
    .max(32, "Debe tener máximo 32 caracteres")
    .required("El nombre de usuario es obligatorio"),
  firstName: Yup.string().required("El nombre es obligatorio"),
  lastName: Yup.string().required("El apellido es obligatorio"),
  email: Yup.string()
    .email("Formato de email inválido")
    .required("El email es obligatorio"),
  password: Yup.string()
    .min(8, "La contraseña debe tener al menos 8 caracteres")
    .max(32, "La contraseña debe tener máximo 32 caracteres")
    .required("La contraseña es obligatoria"),
});

function Register() {
  const [snackbar, setSnackbar] = useState({
    open: false,
    message: "",
    severity: "success",
  });

  const navigate = useNavigate();

  const handleSubmit = async (values, { setSubmitting, resetForm }) => {
    const nuevoUsuario = {
      username: values.username,
      name: values.firstName,
      lastName: values.lastName,
      email: values.email,
      password: values.password,
    };

    try {
      const API_BASE_URL = import.meta.env.VITE_API_URL;
      const response = await fetch(`${API_BASE_URL}/fitstore-api/v1/auth/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(nuevoUsuario),
      });

      if (response.ok) {
        setSnackbar({
          open: true,
          message: "¡Usuario registrado con éxito!",
          severity: "success",
        });
        resetForm();
        setTimeout(() => {
          navigate("/login");
        }, 1500);
      } else {
        const errorData = await response.json();
        setSnackbar({
          open: true,
          message: errorData.message || "Error al registrar el usuario.",
          severity: "error",
        });
      }
    } catch (error) {
      console.error("Error al conectar con la API:", error);
      setSnackbar({
        open: true,
        message: "Error de conexión con el servidor.",
        severity: "error",
      });
    }

    setSubmitting(false);
  };

  return (
    <Container maxWidth="sm">
      <Paper
        elevation={6}
        sx={{
          padding: 4,
          borderRadius: 3,
          marginTop: 8,
          background: "#f9f9f9",
        }}
      >
        <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
          <Avatar sx={{ m: 1, bgcolor: "#2e7d32" }}>
            <PersonAddAltIcon />
          </Avatar>
          <Typography component="h1" variant="h5" sx={{ marginBottom: 2 }}>
            Crear Cuenta
          </Typography>
        </Box>

        <Formik
          initialValues={{
            username: "",
            firstName: "",
            lastName: "",
            email: "",
            password: "",
          }}
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
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <TextField
                    label="Nombre de usuario"
                    name="username"
                    fullWidth
                    variant="outlined"
                    value={values.username}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    error={touched.username && Boolean(errors.username)}
                    helperText={touched.username && errors.username}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Nombre"
                    name="firstName"
                    fullWidth
                    variant="outlined"
                    value={values.firstName}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    error={touched.firstName && Boolean(errors.firstName)}
                    helperText={touched.firstName && errors.firstName}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Apellido"
                    name="lastName"
                    fullWidth
                    variant="outlined"
                    value={values.lastName}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    error={touched.lastName && Boolean(errors.lastName)}
                    helperText={touched.lastName && errors.lastName}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    label="Email"
                    name="email"
                    type="email"
                    fullWidth
                    variant="outlined"
                    value={values.email}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    error={touched.email && Boolean(errors.email)}
                    helperText={touched.email && errors.email}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    label="Contraseña"
                    name="password"
                    type="password"
                    fullWidth
                    variant="outlined"
                    value={values.password}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    error={touched.password && Boolean(errors.password)}
                    helperText={touched.password && errors.password}
                  />
                </Grid>
              </Grid>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                color="success"
                disabled={isSubmitting}
                sx={{ marginTop: 3, paddingY: 1, borderRadius: 2 }}
              >
                Registrarse
              </Button>
            </Form>
          )}
        </Formik>
      </Paper>

      <Snackbar
        open={snackbar.open}
        autoHideDuration={3000}
        onClose={() => setSnackbar((prev) => ({ ...prev, open: false }))}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert
          onClose={() => setSnackbar((prev) => ({ ...prev, open: false }))}
          severity={snackbar.severity}
          sx={{ width: "100%" }}
        >
          {snackbar.message}
        </Alert>
      </Snackbar>
    </Container>
  );
}

export default Register;
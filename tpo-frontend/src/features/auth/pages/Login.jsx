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
import { useNavigate } from "react-router-dom";
import { useUser } from "../context/AuthContext";



const validationSchema = Yup.object({
  email: Yup.string()
    .email("Formato de email inválido")
    .required("El email es obligatorio"),
  password: Yup.string()
    .min(6, "La contraseña debe tener al menos 6 caracteres")
    .required("La contraseña es obligatoria"),
});

function Login() {
  const { setUser } = useUser();
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState("info");
  const navigate = useNavigate();

  const handleSubmit = async (values, { setSubmitting }) => {
    try {
      const response = await fetch(`http://localhost:3001/usuarios?email=${values.email}&password=${values.password}`);
      const data = await response.json();
  
      if (data.length > 0) {
        const usuario = data[0];
        localStorage.setItem("usuario", JSON.stringify(usuario));
        setUser(usuario);
  
        setSnackbarMessage("✅ ¡Entraste!");
        setSnackbarSeverity("success");
        setOpenSnackbar(true);
  
        setTimeout(() => {
          navigate("/");
        }, 1500);
      } else {
        setSnackbarMessage("❌ Usuario no encontrado");
        setSnackbarSeverity("error");
        setOpenSnackbar(true);
      }
    } catch (error) {
      console.error("Error al hacer login:", error);
      setSnackbarMessage("❌ Error de conexión");
      setSnackbarSeverity("error");
      setOpenSnackbar(true);
    }
  
    setSubmitting(false);
  };

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
          initialValues={{ email: "", password: "" }}
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
                label="Email"
                name="email"
                type="email"
                value={values.email}
                onChange={handleChange}
                onBlur={handleBlur}
                error={touched.email && Boolean(errors.email)}
                helperText={touched.email && errors.email}
              />
              <TextField
                margin="normal"
                fullWidth
                label="Contraseña"
                name="password"
                type="password"
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
                disabled={isSubmitting}
                sx={{
                  marginTop: 2,
                  backgroundColor: "#FA9500",
                  color: "black",
                }}
              >
                Iniciar sesión
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

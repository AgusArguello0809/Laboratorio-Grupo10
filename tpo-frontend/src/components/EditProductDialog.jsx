import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Stack,
  MenuItem,
  Snackbar,
  Alert,
  Typography
} from "@mui/material";
import { useState, useEffect } from "react";
import ProductImageSlider from "./ProductImageSlider";

const categorias = [
  "Electrónica",
  "Hogar",
  "Ropa",
  "Deportes",
  "Libros",
  "Accesorios"
];

export default function EditProductDialog({ open, onClose, product, onSave }) {
  const [form, setForm] = useState({
    title: "",
    price: "",
    category: "",
    stock: "",
    description: ""
  });

  const [images, setImages] = useState([]);
  const [error, setError] = useState(false);

  useEffect(() => {
    if (open && product) {
      setForm({
        title: product.title || "",
        price: product.price || "",
        category: product.category || "",
        stock: product.stock || "",
        description: product.description || ""
      });
      setImages(product.images || []);
    }
  }, [open, product]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "price") {
      if(/[^0-9.,]/.test(value)) return;
      const cleanValue = value.replace(",", ".");
      if (!/^\d*\.?\d{0,2}$/.test(cleanValue)) return;
      const floatVal = parseFloat(cleanValue);
      if (isNaN(floatVal) || floatVal < 0) return;
      setForm((prev) => ({ ...prev, [name]: cleanValue }));
    } else if (name === "stock") {
      if(/[^0-9]/.test(value)) return;
      const intVal = parseInt(value);
      if (isNaN(intVal) || intVal < 0) return;
      setForm((prev) => ({ ...prev, [name]: intVal }));
    } else {
      setForm((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleSave = () => {
    const isValid =
      form.title.trim() !== "" &&
      form.category.trim() !== "" &&
      form.price !== "" && !isNaN(form.price) &&
      form.stock !== "" && !isNaN(form.stock) &&
      form.description.trim() !== "" &&
      images.length > 0;

    if (!isValid) {
      setError(true);
      return;
    }

    onSave({
      ...product,
      ...form,
      price: parseFloat(form.price),
      stock: parseInt(form.stock, 10),
      images
    });
    onClose();
  };

  return (
    <>
      <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
        <DialogTitle>Editar producto</DialogTitle>
        <DialogContent dividers>
          <Typography variant="subtitle2" color="text.secondary" mb={1}>
            Imágenes del producto (máx 10)
          </Typography>

          <ProductImageSlider images={images} setImages={setImages} />

          <Stack spacing={2} mt={3}>
            <TextField
              label="Título"
              name="title"
              fullWidth
              value={form.title}
              onChange={handleChange}
            />
            <TextField
              label="Precio"
              name="price"
              fullWidth
              value={form.price}
              onChange={handleChange}
              slotProps={{
                input: {
                  min: 1
                }
              }}
            />
            <TextField
              select
              label="Categoría"
              name="category"
              fullWidth
              value={form.category}
              onChange={handleChange}
            >
              {categorias.map((cat) => (
                <MenuItem key={cat} value={cat}>
                  {cat}
                </MenuItem>
              ))}
            </TextField>
            <TextField
              label="Stock"
              name="stock"
              fullWidth
              value={form.stock}
              onChange={handleChange}
              slotProps={{
                input: {
                  min: 1
                }
              }}
            />
            <TextField
              label="Descripción"
              name="description"
              fullWidth
              multiline
              rows={20}
              value={form.description}
              onChange={handleChange}
            />
          </Stack>
        </DialogContent>

        <DialogActions>
          <Button onClick={onClose}>Cancelar</Button>
          <Button onClick={handleSave} variant="contained" color="primary">
            Guardar
          </Button>
        </DialogActions>
      </Dialog>

      <Snackbar
        open={error}
        autoHideDuration={3000}
        onClose={() => setError(false)}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert
          onClose={() => setError(false)}
          severity="warning"
          variant="filled"
        >
          Completá todos los campos e incluí al menos una imagen
        </Alert>
      </Snackbar>
    </>
  );
}
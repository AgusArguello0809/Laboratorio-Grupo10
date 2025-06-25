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
import { useState, useEffect, useRef } from "react";
import ProductImageSlider from "./ProductImageSlider";
import { normalizeImages } from "../../utils/normalizeImages";
import { useProductService } from "../../hooks/useProductService";

const categorias = [
  { id: 1, name: "Calzado" },
  { id: 2, name: "Equipamiento" },
  { id: 3, name: "Ropa" },
  { id: 4, name: "Suplementos" },
  { id: 5, name: "Accesorios" }
];

export default function EditProductDialog({ open, onClose, product, onSave }) {
  const [originalForm, setOriginalForm] = useState(null);
  const [originalImages, setOriginalImages] = useState([]);
  const [form, setForm] = useState({
    title: "",
    price: "",
    category: "",
    stock: "",
    description: ""
  });

  const [images, setImages] = useState([]);
  const [error, setError] = useState(false);
  const { updateProduct } = useProductService();

  const prevProductIdRef = useRef(null);

  useEffect(() => {
    if (open && product && product.id !== prevProductIdRef.current) {
      prevProductIdRef.current = product.id;

      const initialForm = {
        title: product.title || "",
        price: product.price || "",
        category: product.categoryId?.toString() || product.category || "",
        stock: product.stock || "",
        description: product.description || ""
      };

      const normalizedImages = normalizeImages(product.images || []);

      setForm(initialForm);
      setImages(normalizedImages);
      setOriginalForm(initialForm);
      setOriginalImages(normalizedImages);
    }
  }, [open, product]);

  const atLeastOneImage =
    images.some((img) =>
      typeof img === "string" ||
      (img && img.file instanceof File) ||
      img instanceof File
    );

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "price") {
      if (/[^0-9.,]/.test(value)) return;
      const cleanValue = value.replace(",", ".");
      if (value === "") return setForm((prev) => ({ ...prev, [name]: "" }));
      if (!/^\d*\.?\d{0,2}$/.test(cleanValue)) return;
      const floatVal = parseFloat(cleanValue);
      if (isNaN(floatVal) || floatVal < 1) return;
      setForm((prev) => ({ ...prev, [name]: cleanValue }));

    } else if (name === "stock") {
      if (/[^0-9]/.test(value)) return;
      if (value === "") return setForm((prev) => ({ ...prev, [name]: "" }));
      const intVal = parseInt(value);
      if (isNaN(intVal) || intVal < 0) return;
      setForm((prev) => ({ ...prev, [name]: intVal }));

    } else {
      setForm((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleSave = async () => {
    const isValid =
      form.title.trim() !== "" &&
      form.category &&
      form.price !== "" && !isNaN(form.price) &&
      form.stock !== "" && !isNaN(form.stock) &&
      form.description.trim() !== "" &&
      atLeastOneImage;

    if (!isValid) {
      setError(true);
      return;
    }

    const productoEditado = {
      id: product.id,
      title: form.title.trim(),
      description: form.description.trim(),
      price: parseFloat(form.price),
      stock: parseInt(form.stock, 10),
      categoryId: parseInt(form.category),
      images: images // ya viene normalizado
    };

    try {
      const result = await updateProduct(productoEditado);

      if (result.success) {
        onSave?.(result.data); // trigger para refrescar vista si es necesario
        onClose();
      } else {
        setError(true);
      }
    } catch (err) {
      console.error("Error inesperado al actualizar producto:", err);
      setError(true);
    }
  };

  const handleClose = () => {
    if (originalForm) setForm(originalForm);
    if (originalImages) setImages(originalImages);
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
                <MenuItem key={cat.id} value={cat.id}>
                  {cat.name}
                </MenuItem>
              ))}
            </TextField>
            <TextField
              label="Stock"
              name="stock"
              fullWidth
              value={form.stock}
              onChange={handleChange}
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
          <Button onClick={handleClose}>Cancelar</Button>
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
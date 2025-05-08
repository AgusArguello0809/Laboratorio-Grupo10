import {
  TextField,
  MenuItem,
  Typography,
  Stack,
  Box
} from "@mui/material";

const categorias = [
  "Electrónica",
  "Entrenamiento",
  "Ropa",
  "Suplementos",
  "Accesorios"
];

export default function ProductForm({ formData, setFormData }) {
const handleChange = (e) => {
  const { name, value } = e.target;

  if (name === "price") {
    if (/[^0-9.,]/.test(value)) return;

    const cleanValue = value.replace(",", ".");

    if (value === "") {
      setFormData((prev) => ({ ...prev, [name]: "" }));
      return;
    }

    if (!/^\d*\.?\d{0,2}$/.test(cleanValue)) return;
    const floatVal = parseFloat(cleanValue);
    if (isNaN(floatVal) || floatVal < 1) return;

    setFormData((prev) => ({ ...prev, [name]: cleanValue }));

  } else if (name === "stock") {
    if (/[^0-9]/.test(value)) return;

    if (value === "") {
      setFormData((prev) => ({ ...prev, [name]: "" }));
      return;
    }

    const intVal = parseInt(value);
    if (isNaN(intVal) || intVal < 1) return;

    setFormData((prev) => ({ ...prev, [name]: intVal }));

  } else {
    setFormData((prev) => ({ ...prev, [name]: value }));
  }
};

  return (
    <Box
      sx={{
        width: "100%",
        backgroundColor: "#f1f1f1",
        padding: 3,
        borderRadius: 2,
        boxShadow: 3
      }}
    >
      <Typography variant="h6" color="#000" fontWeight="bold" gutterBottom>
        Detalles del producto
      </Typography>

      <Stack spacing={2}>
        <TextField
          label="Título"
          name="title"
          fullWidth
          value={formData.title}
          onChange={handleChange}
          sx={textFieldStyle}
        />

        <TextField
          select
          label="Categoría"
          name="category"
          fullWidth
          value={formData.category}
          onChange={handleChange}
          sx={textFieldStyle}
        >
          {categorias.map((cat) => (
            <MenuItem key={cat} value={cat}>
              {cat}
            </MenuItem>
          ))}
        </TextField>

        <TextField
          label="Precio"
          name="price"
          fullWidth
          value={formData.price}
          onChange={handleChange}
          slotProps={{
            input: {
              inputMode: "numeric",
              pattern: "[0-9]*"
            }
          }}
          sx={textFieldStyle}
        />

        <TextField
          label="Stock"
          name="stock"
          fullWidth
          value={formData.stock}
          onChange={handleChange}
          slotProps={{
            input: {
              inputMode: "numeric",
              pattern: "[0-9]*"
            }
          }}
          sx={textFieldStyle}
        />

        <TextField
          label="Descripción"
          name="description"
          fullWidth
          multiline
          rows={30}
          value={formData.description}
          onChange={handleChange}
          sx={textFieldStyle}
        />
      </Stack>
    </Box>
  );
}

// Estilo común
const textFieldStyle = {
  backgroundColor: "#fff",
  "& .MuiInputBase-input": { color: "black" },
  "& .MuiInputLabel-root": {
    color: "#555",
    backgroundColor: "#fff",
    px: 0.5
  },
  "& .MuiInputLabel-root.Mui-focused": { color: "#1976d2" }
};
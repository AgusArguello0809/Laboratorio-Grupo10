import { useState } from "react";
import { Box, Button, Stack, Typography, Snackbar, Alert } from "@mui/material";
import ProductImageSlider from "../components/management/ProductImageSlider";
import ProductForm from "../components/management/ProductForm";
import ConfirmCancelDialog from "../components/management/ConfirmCancelDialog";
import DraftPreviewDialog from "../components/management/DraftPreviewDialog";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../auth/context/AuthContext";
import { useProductService } from "../hooks/useProductService";

export default function SellProduct() {
  const { user } = useAuth();
  const { addProduct, loading } = useProductService();
  const navigate = useNavigate();

  const [images, setImages] = useState([]);
  const [formData, setFormData] = useState({
    title: "",
    category: "",
    price: "",
    stock: "",
    description: ""
  });
  const [openConfirm, setOpenConfirm] = useState(false);
  const [openDraft, setOpenDraft] = useState(false);
  const [validationError, setValidationError] = useState(false);

  const isFormValid =
    formData.title.trim() !== "" &&
    formData.category.trim() !== "" &&
    parseFloat(formData.price) > 0 &&
    parseInt(formData.stock) >= 0 &&
    formData.description.trim() !== "" &&
    images.length > 0;

  const handleCancel = () => setOpenConfirm(true);

  const handleConfirmCancel = () => {
    setOpenConfirm(false);
    navigate("/");
  };

  const handleSave = () => {
    if (!isFormValid) {
      setValidationError(true);
      return;
    }
    setOpenDraft(true);
  };

  const handleConfirmSave = async () => {
    const nuevoProducto = {
      ...formData,
      images,
      ownerId: user.id,
      price: parseFloat(formData.price),
      stock: parseInt(formData.stock)
    };

    try {
      const result = await addProduct(nuevoProducto);
      if (result.success) {
        navigate("/my-publications");
      } else {
        console.error("Error al guardar el producto:", result.error);
      }
    } catch (error) {
      console.error("Error al guardar el producto:", error);
    }
  };

  return (
    <Box sx={{ p: 4 }}>
      <Typography variant="h4" gutterBottom fontWeight="bold">Publicar producto</Typography>
      <Typography variant="subtitle2" mb={2}>Imágenes del producto (máx 10)</Typography>
      <Box sx={{
        borderRadius: "8px", backgroundColor: "#f4f4f4", minHeight: "300px",
        display: "flex", alignItems: "center", justifyContent: "center", mb: 4, position: "relative"
      }}>
        <ProductImageSlider images={images} setImages={setImages} />
      </Box>
      <Box sx={{
        borderRadius: "8px", minHeight: "300px",
        display: "flex", alignItems: "center", justifyContent: "center", mb: 4, position: "relative"
      }}>
        <ProductForm formData={formData} setFormData={setFormData} />
      </Box>
      <Stack direction="row" spacing={2} justifyContent="flex-end">
        <Button variant="outlined" color="error" onClick={handleCancel} disabled={loading}>
          Cancelar
        </Button>
        <Button
          variant="contained"
          color="primary"
          onClick={handleSave}
          disabled={loading}
        >
          {loading ? "Guardando..." : "Guardar"}
        </Button>
      </Stack>
      <ConfirmCancelDialog
        open={openConfirm}
        onClose={() => setOpenConfirm(false)}
        onConfirm={handleConfirmCancel}
      />
      <DraftPreviewDialog
        open={openDraft}
        onClose={() => setOpenDraft(false)}
        onConfirm={handleConfirmSave}
        product={{ ...formData, images }}
      />
      <Snackbar
        open={validationError}
        autoHideDuration={3000}
        onClose={() => setValidationError(false)}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert
          onClose={() => setValidationError(false)}
          severity="warning"
          variant="filled"
        >
          Completá todos los campos e incluí al menos una imagen
        </Alert>
      </Snackbar>
    </Box>
  );
}
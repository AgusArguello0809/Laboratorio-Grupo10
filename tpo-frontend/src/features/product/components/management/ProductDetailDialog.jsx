import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Typography,
  Box,
  IconButton,
} from "@mui/material";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { useState } from "react";

export default function ProductDetailDialog({ open, onClose, product }) {
  const [currentImage, setCurrentImage] = useState(0);

  if (!product) return null;

  const {
    title = "Sin título",
    price = "N/A",
    category = "Sin categoría",
    stock = 0,
    description = "Sin descripción",
    images = [],
  } = product;

  const handleNext = () =>
    setCurrentImage((prev) => (prev + 1) % images.length);

  const handlePrev = () =>
    setCurrentImage((prev) =>
      prev === 0 ? images.length - 1 : prev - 1
    );

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent dividers>
        <Box display="flex" flexDirection="column" alignItems="center" mb={2}>
          <Box display="flex" alignItems="center">
            <IconButton onClick={handlePrev} disabled={!images || images.length <= 1}>
              <ArrowBackIosIcon />
            </IconButton>
            <img
              src={
                Array.isArray(images) && images.length > 0
                  ? images[currentImage]
                  : ""
              }
              alt={title}
              style={{ width: "300px", height: "auto", margin: "0 12px" }}
            />
            <IconButton onClick={handleNext} disabled={!images || images.length <= 1}>
              <ArrowForwardIosIcon />
            </IconButton>
          </Box>

          {images.length > 1 && (
            <Box display="flex" justifyContent="center" mt={1}>
              {images.map((_, idx) => (
                <Box
                  key={idx}
                  sx={{
                    width: 10,
                    height: 10,
                    borderRadius: "50%",
                    mx: 0.5,
                    backgroundColor: idx === currentImage ? "#FA9500" : "gray",
                    opacity: currentImage === idx ? 1 : 0.4,
                    transition: "all 0.2s"
                  }}
                />
              ))}
            </Box>
          )}
        </Box>
        <Typography variant="body2" gutterBottom>
          <strong>Precio:</strong> ${price}
        </Typography>
        <Typography variant="body2" gutterBottom>
          <strong>Categoría:</strong> {category}
        </Typography>
        <Typography variant="body2" gutterBottom>
          <strong>Stock:</strong> {stock}
        </Typography>
        <Typography variant="body2" gutterBottom>
          <strong>Descripción:</strong> {description}
        </Typography>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cerrar</Button>
      </DialogActions>
    </Dialog>
  );
}
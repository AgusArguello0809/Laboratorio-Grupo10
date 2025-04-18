import { useState, useRef } from "react";
import {
  Box,
  IconButton,
  Button,
  Stack
} from "@mui/material";
import AddPhotoAlternateIcon from "@mui/icons-material/AddPhotoAlternate";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import DeleteIcon from "@mui/icons-material/Delete";

export default function ProductImageSlider({ images, setImages }) {
  const [currentIndex, setCurrentIndex] = useState(0);
  const inputRef = useRef();

  const handleFileChange = (e) => {
    const files = Array.from(e.target.files);
    const urls = files.map((file) => URL.createObjectURL(file));
    const newImages = [...images, ...urls].slice(0, 10);
    setImages(newImages);
    if (currentIndex >= newImages.length) {
      setCurrentIndex(newImages.length - 1);
    }
  };

  const handlePrev = () => {
    setCurrentIndex((prev) => (prev === 0 ? images.length - 1 : prev - 1));
  };

  const handleNext = () => {
    setCurrentIndex((prev) => (prev === images.length - 1 ? 0 : prev + 1));
  };

  const handleDeleteImage = () => {
    const newImages = images.filter((_, i) => i !== currentIndex);
    setImages(newImages);
    if (currentIndex >= newImages.length) {
      setCurrentIndex(Math.max(0, newImages.length - 1));
    }
  };

  const triggerFileInput = () => {
    inputRef.current.click();
  };

  return (
    <Box
      sx={{
        position: "relative",
        width: "100%",
        maxWidth: 500,
        height: 300,
        border: "2px dashed #ccc",
        borderRadius: 2,
        backgroundColor: "#fff",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        overflow: "hidden",
        flexDirection: "column",
        px: 2
      }}
    >
      {images.length > 0 ? (
        <>
          <img
            src={images[currentIndex]}
            alt={`imagen-${currentIndex}`}
            style={{
              width: "100%",
              height: "100%",
              objectFit: "contain"
            }}
          />

          {images.length > 1 && (
            <>
              <IconButton
                onClick={handlePrev}
                sx={{
                  position: "absolute",
                  top: "50%",
                  left: 8,
                  transform: "translateY(-50%)",
                  backgroundColor: "rgba(0,0,0,0.5)",
                  color: "white",
                  "&:hover": {
                    backgroundColor: "rgba(0,0,0,0.8)"
                  }
                }}
              >
                <ArrowBackIosIcon fontSize="small" />
              </IconButton>
              <IconButton
                onClick={handleNext}
                sx={{
                  position: "absolute",
                  top: "50%",
                  right: 8,
                  transform: "translateY(-50%)",
                  backgroundColor: "rgba(0,0,0,0.5)",
                  color: "white",
                  "&:hover": {
                    backgroundColor: "rgba(0,0,0,0.8)"
                  }
                }}
              >
                <ArrowForwardIosIcon fontSize="small" />
              </IconButton>
            </>
          )}

          {/* Indicadores */}
          <Box
            sx={{
              position: "absolute",
              bottom: 8,
              display: "flex",
              gap: 1
            }}
          >
            {images.map((_, index) => (
              <Box
                key={index}
                sx={{
                  width: 10,
                  height: 10,
                  borderRadius: "50%",
                  backgroundColor: index === currentIndex ? "black" : "#aaa"
                }}
              />
            ))}
          </Box>

          {/* Botones de acción */}
          <Box
            sx={{
              position: "absolute",
              top: 8,
              right: 8,
              display: "flex",
              gap: 1
            }}
          >
            {images.length < 10 && (
              <Button
                size="small"
                onClick={triggerFileInput}
                sx={{
                  backgroundColor: "rgba(255,255,255,0.9)",
                  "&:hover": {
                    backgroundColor: "rgba(255,255,255,1)"
                  }
                }}
                variant="outlined"
                startIcon={<AddPhotoAlternateIcon />}
              >
                Agregar
              </Button>
            )}

            {images.length > 0 && (
              <IconButton onClick={handleDeleteImage} color="error">
                <DeleteIcon />
              </IconButton>
            )}
          </Box>
        </>
      ) : (
        <Stack alignItems="center" spacing={1}>
          <Button
            variant="outlined"
            component="label"
            startIcon={<AddPhotoAlternateIcon />}
          >
            Cargar imagen
            <input
              type="file"
              hidden
              multiple
              accept="image/*"
              onChange={handleFileChange}
              ref={inputRef}
            />
          </Button>
        </Stack>
      )}

      {/* Hidden input */}
      <input
        type="file"
        hidden
        multiple
        accept="image/*"
        ref={inputRef}
        onChange={handleFileChange}
      />
    </Box>
  );
}
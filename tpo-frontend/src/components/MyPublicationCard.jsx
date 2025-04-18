import { useState } from "react";
import {
  Card,
  CardMedia,
  CardContent,
  Typography,
  CardActions,
  IconButton,
  Box,
  Collapse
} from "@mui/material";
import VisibilityIcon from "@mui/icons-material/Visibility";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import ProductDetailDialog from "./ProductDetailDialog";
import EditProductDialog from "./EditProductDialog";
import DeleteConfirmDialog from "./DeleteConfirmDialog";

export default function MyPublicationCard({ product, onEdit, onDelete }) {
  if (!product || typeof product !== "object") return null;

  const {
    title = "Sin título",
    price = "N/A",
    category = "Sin categoría",
    stock = 0,
    description = "Sin descripción",
    images = []
  } = product;

  const [expanded, setExpanded] = useState(false);
  const [currentImage, setCurrentImage] = useState(0);
  const [detailOpen, setDetailOpen] = useState(false);
  const [editOpen, setEditOpen] = useState(false);
  const [deleteOpen, setDeleteOpen] = useState(false);

  const toggleExpand = () => setExpanded(!expanded);

  const nextImage = (e) => {
    e.stopPropagation();
    setCurrentImage((prev) => (prev + 1) % images.length);
  };

  const prevImage = (e) => {
    e.stopPropagation();
    setCurrentImage((prev) =>
      prev === 0 ? images.length - 1 : prev - 1
    );
  };

  return (
    <>
      <Card
        sx={{
          width: 300,
          m: 2,
          cursor: "pointer",
          backgroundColor: "#f9f9f9",
          position: "relative",
          transition: "box-shadow 0.3s",
          "&:hover": {
            boxShadow: 6
          }
        }}
        onClick={toggleExpand}
      >
        <Box position="relative">
          <CardMedia
            component="img"
            height="180"
            image={images?.[currentImage]}
            alt={title}
            sx={{ objectFit: "contain", backgroundColor: "#fff" }}
          />
          {expanded && images?.length > 1 && (
            <>
              <IconButton
                onClick={prevImage}
                sx={{
                  position: "absolute",
                  top: "50%",
                  left: 8,
                  transform: "translateY(-50%)",
                  backgroundColor: "rgba(0,0,0,0.5)",
                  color: "white"
                }}
              >
                <ArrowBackIosIcon fontSize="small" />
              </IconButton>
              <IconButton
                onClick={nextImage}
                sx={{
                  position: "absolute",
                  top: "50%",
                  right: 8,
                  transform: "translateY(-50%)",
                  backgroundColor: "rgba(0,0,0,0.5)",
                  color: "white"
                }}
              >
                <ArrowForwardIosIcon fontSize="small" />
              </IconButton>
            </>
          )}
        </Box>

        <CardContent>
          <Typography variant="h6">{title}</Typography>
          <Typography variant="subtitle1">${price}</Typography>
        </CardContent>

        <Collapse in={expanded} timeout="auto" unmountOnExit>
          <CardContent sx={{ pt: 0 }}>
            <Typography
              variant="body2"
              color="text.secondary"
              sx={{ display: "flex", justifyContent: "space-between" }}
            >
              <span>{category}</span>
              <span>Stock: {stock}</span>
            </Typography>
            <Typography
              variant="body2"
              mt={1}
              sx={{
                display: "-webkit-box",
                WebkitLineClamp: 3,
                WebkitBoxOrient: "vertical",
                overflow: "hidden",
                textOverflow: "ellipsis"
              }}
            >
              {description}
            </Typography>
          </CardContent>

          <CardActions sx={{ justifyContent: "space-between", px: 2 }}>
            <IconButton onClick={(e) => { e.stopPropagation(); setDetailOpen(true); }}>
              <VisibilityIcon />
            </IconButton>
            <IconButton onClick={(e) => { e.stopPropagation(); setEditOpen(true); }}>
              <EditIcon />
            </IconButton>
            <IconButton onClick={(e) => { e.stopPropagation(); setDeleteOpen(true); }}>
              <DeleteIcon />
            </IconButton>
          </CardActions>
        </Collapse>
      </Card>

      {/* Dialogs */}
      <ProductDetailDialog
        open={detailOpen}
        onClose={() => setDetailOpen(false)}
        product={product}
      />

      <EditProductDialog
        open={editOpen}
        onClose={() => setEditOpen(false)}
        product={product}
        onSave={(updatedProduct) => {
          onEdit?.(updatedProduct);
          setEditOpen(false);
        }}
      />

      <DeleteConfirmDialog
        open={deleteOpen}
        onClose={() => setDeleteOpen(false)}
        onConfirm={() => {
          onDelete?.();
          setDeleteOpen(false);
        }}
      />
    </>
  );
}
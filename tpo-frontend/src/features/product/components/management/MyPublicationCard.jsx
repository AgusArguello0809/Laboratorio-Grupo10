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
import { getCategoryName } from "../../../config/categories"

export default function MyPublicationCard({ product, onEdit, onDelete }) {

  const {
    title = "Sin título",
    price = 0,
    category = 1,
    stock = 0,
    description = "Sin descripción",
    images = []
  } = product;

  const [expanded, setExpanded] = useState(false);
  const [currentImage, setCurrentImage] = useState(0);
  const [detailOpen, setDetailOpen] = useState(false);
  const [editOpen, setEditOpen] = useState(false);
  const [deleteOpen, setDeleteOpen] = useState(false);

  if (!product || typeof product !== "object") return null;

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
          <Box
            sx={{
              height: "200px",
              width: "100%",
              backgroundColor: "#fff",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              borderRadius: 1,
              overflow: "hidden"
            }}
          >
            <CardMedia
              component="img"
              image={images?.[currentImage]}
              alt={title}
              sx={{
                maxHeight: "100%",
                maxWidth: "100%",
                objectFit: "contain"
              }}
            />
          </Box>

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

              <Box
                sx={{
                  position: "absolute",
                  bottom: 8,
                  left: "50%",
                  transform: "translateX(-50%)",
                  display: "flex",
                  gap: "6px"
                }}
              >
                {images.map((_, index) => (
                  <Box
                    key={index}
                    sx={{
                      width: 8,
                      height: 8,
                      borderRadius: "50%",
                      backgroundColor: currentImage === index ? "#FA9500" : "gray",
                      opacity: currentImage === index ? 1 : 0.4,
                      transition: "all 0.2s"
                    }}
                  />
                ))}
              </Box>
            </>
          )}
        </Box>

        <CardContent>
          <Typography variant="h6">{title}</Typography>
          <Typography variant="subtitle1">
            ${!isNaN(parseFloat(product.price)) ? parseFloat(product.price).toFixed(2) : "0.00"}
          </Typography>

          <Typography
            variant="body2"
            color="text.secondary"
            sx={{ display: "flex", justifyContent: "space-between", mb: 0.5 }}
          >
            <span>{getCategoryName(product.categoryId)}</span>
            <span>Stock: {stock}</span>
          </Typography>

          <Typography
            variant="body2"
            color="text.secondary"
            sx={{
              display: "-webkit-box",
              WebkitLineClamp: 3,
              WebkitBoxOrient: "vertical",
              overflow: "hidden",
              textOverflow: "ellipsis",
              whiteSpace: "normal",
              minHeight: "3.6em",
              wordBreak: "break-word"
            }}
          >
            {description}
          </Typography>
        </CardContent>

        <Collapse in={expanded} timeout="auto" unmountOnExit>
          <CardActions sx={{ justifyContent: "space-between", px: 2 }}>
            <IconButton
              onClick={(e) => { e.stopPropagation(); setDetailOpen(true); }}
              sx={{
                color: "gray",
                transition: "color 0.2s",
                "&:hover": { color: "#FA9500" }
              }}
            >
              <VisibilityIcon />
            </IconButton>
            <IconButton
              onClick={(e) => { e.stopPropagation(); setEditOpen(true); }}
              sx={{
                color: "gray",
                transition: "color 0.2s",
                "&:hover": { color: "#FA9500" }
              }}
            >
              <EditIcon />
            </IconButton>
            <IconButton
              onClick={(e) => { e.stopPropagation(); setDeleteOpen(true); }}
              sx={{
                color: "gray",
                transition: "color 0.2s",
                "&:hover": { color: "#FA9500" }
              }}
            >
              <DeleteIcon />
            </IconButton>
          </CardActions>
        </Collapse>
      </Card>

      {/* Dialogs */}
      <ProductDetailDialog
        open={detailOpen}
        onClose={() => setDetailOpen(false)}
        product={{
          ...product,
          category: { name: getCategoryName(product.categoryId) }
        }}
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
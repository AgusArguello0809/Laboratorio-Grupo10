import { Card, CardActionArea, Box, Typography } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";

export default function EmptyProductCard({ onClick }) {
  return (
    <Card
      sx={{
        width: 300,
        height: 280,
        m: 2,
        backgroundColor: "#f0f0f0",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        border: "2px dashed #ccc",
        cursor: "pointer",
        transition: "0.3s",
        "&:hover": {
          borderColor: "#000",
          backgroundColor: "#e0e0e0"
        }
      }}
      onClick={onClick}
    >
      <CardActionArea
        sx={{
          height: "100%",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center"
        }}
      >
        <Box
          sx={{
            width: 56,
            height: 56,
            backgroundColor: "black",
            color: "white",
            borderRadius: "50%",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            mb: 1
          }}
        >
          <AddIcon />
        </Box>
        <Typography variant="body1" fontWeight="bold">
          Agregar producto
        </Typography>
      </CardActionArea>
    </Card>
  );
}
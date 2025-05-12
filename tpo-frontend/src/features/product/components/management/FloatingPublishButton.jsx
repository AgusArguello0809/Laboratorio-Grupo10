import { Fab } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import { useNavigate } from "react-router-dom";

export default function FloatingPublishButton() {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/sell");
  };

  return (
    <Fab
      color="primary"
      variant="extended"
      sx={{
        position: "fixed",
        bottom: 16,
        right: 16,
        backgroundColor: "black",
        color: "white",
        "&:hover": {
          backgroundColor: "#333"
        }
      }}
      onClick={handleClick}
    >
      <AddIcon sx={{ mr: 1 }} />
      Publicar
    </Fab>
  );
}
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    Typography
  } from "@mui/material";
  
  export default function DeleteConfirmDialog({ open, onClose, onConfirm }) {
    return (
      <Dialog open={open} onClose={onClose}>
        <DialogTitle>¿Eliminar publicación?</DialogTitle>
        <DialogContent>
          <Typography>Esta acción no se puede deshacer.</Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose}>Cancelar</Button>
          <Button onClick={onConfirm} color="error" variant="contained">
            Eliminar
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
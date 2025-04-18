import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    Typography
  } from "@mui/material";
  
  export default function ConfirmCancelDialog({ open, onClose, onConfirm }) {
    return (
      <Dialog open={open} onClose={onClose}>
        <DialogTitle>¿Seguro?</DialogTitle>
        <DialogContent>
          <Typography>
            Si cancelás ahora, se perderán los cambios y volverás al inicio.
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose} color="primary">
            No
          </Button>
          <Button onClick={onConfirm} color="error" variant="contained">
            Sí
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
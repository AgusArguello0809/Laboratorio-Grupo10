import { Navigate, useLocation } from 'react-router-dom';
import { useUser } from '../context/AuthProvider';
import { CircularProgress, Box } from '@mui/material';

const PrivateRoute = ({ children }) => {
  const { user, loading } = useUser();
  const location = useLocation();

  if (loading) {
    return (
      <Box sx={{ 
        display: 'flex', 
        justifyContent: 'center', 
        alignItems: 'center', 
        height: '100vh' 
      }}>
        <CircularProgress />
      </Box>
    );
  }

  if (!user) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // Si está autenticado, renderizar el componente hijo
  return children;
};

export default PrivateRoute;
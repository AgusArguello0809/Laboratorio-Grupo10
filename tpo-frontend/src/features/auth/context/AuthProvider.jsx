import { createContext, useContext, useState, useEffect } from "react";
import { 
  login as loginService, 
  logout as logoutService, 
  getCurrentUser, 
  isAuthenticated 
} from "../services/authService";

const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isInitialized, setIsInitialized] = useState(false);

  // Verificar autenticación al cargar la página
  useEffect(() => {
    const checkAuth = () => {
      if (isAuthenticated()) {
        setUser(getCurrentUser());
      }
      setIsInitialized(true);
      setLoading(false);
    };

    checkAuth();
  }, []);

  const login = async (email, password) => {
    setLoading(true);
    setError(null);

    try {
      const data = await loginService(email, password);
      setUser(data.user);
      return { success: true };
    } catch (err) {
      setError(err.message);
      return { success: false, error: err.message };
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    logoutService();
    setUser(null);
  };

  const value = {
    user,
    setUser, 
    loading,
    error,
    login,
    logout,
    isInitialized,
    isAuthenticated: () => !!user
  };

  return (
    <UserContext.Provider value={value}>
      {children}
    </UserContext.Provider>
  );
};
export const useUser = () => {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error("useUser debe usarse dentro de un UserProvider");
  }
  return context;
};
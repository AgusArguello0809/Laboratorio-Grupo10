import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

const API_URL = 'http://localhost:3001';

// Configurar interceptor para añadir token a todas las peticiones
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export const login = async (email, password) => {
  try {
    const response = await axios.post(`${API_URL}/auth/login`, { email, password });
    
    if (response.data.token) {
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('user', JSON.stringify(response.data.user));
    }
    
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Error al iniciar sesión');
  }
};

export const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
};

// Verificar si el token es válido
export const isAuthenticated = () => {
  const token = localStorage.getItem('token');
  
  if (!token) {
    return false;
  }
  
  try {
    const decoded = jwtDecode(token);
    const currentTime = Date.now() / 1000;
    
    console.log(decoded.exp)
    console.log(currentTime)
    // Verificar si el token ha expirado
    if (decoded.exp < currentTime) {
      logout();
      return false;
    }
    
    return true;
  } catch (err) {
    logout();
    return false;
  }
};

export const getCurrentUser = () => {
  try {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  } catch (error) {
    logout();
    return null;
  }
};

export const getToken = () => {
  return localStorage.getItem('token');
};
import { jwtDecode } from 'jwt-decode';

const API_BASE_URL = `${import.meta.env.VITE_API_URL}/fitstore-api/v1`;

export const login = async (username, password) => {
  try {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        password: password,
      }),
    });

    if (!response.ok) {
      const errorData = await response.json();

      const error = new Error(errorData.message || "Error de autenticación");
      error.errorCode = errorData.errorCode;
      throw error;
    }

    const data = await response.json();
    console.log("Respuesta del login:", data);

    if (data.jwt) {
      localStorage.setItem("token", data.jwt);

      try {
        const decoded = jwtDecode(data.jwt);
        console.log("JWT decodificado:", decoded);

        const user = {
          username: decoded.sub,
          id: decoded.id,
        };

        console.log(user)
        localStorage.setItem("user", JSON.stringify(user));

        return {
          success: true,
          user: user,
          token: data.jwt
        };
      } catch (decodeError) {
        console.error("Error decodificando JWT:", decodeError);
        throw new Error("Token inválido recibido del servidor");
      }
    } else {
      throw new Error("No se recibió token de autenticación");
    }
  } catch (error) {
    console.error("Error en login:", error);
    throw error;
  }
};

export const logout = () => {
  localStorage.removeItem("token");
  sessionStorage.removeItem("token");
};

export const getToken = () => {
  return localStorage.getItem("token") || sessionStorage.getItem("token");
};

export const isAuthenticated = () => {
  const token = getToken();
  if (!token) return false;

  try {
    const decoded = jwtDecode(token);
    const currentTime = Date.now() / 1000;

    // Verificar si el token ha expirado
    if (decoded.exp && decoded.exp < currentTime) {
      logout();
      return false;
    }

    return true;
  } catch (error) {
    console.error("Error validando token:", error);
    logout(); // Limpiar token inválido
    return false;
  }
};

export const getCurrentUser = () => {
  const stored = localStorage.getItem("user");

  if (stored) {
    try {
      return JSON.parse(stored);
    } catch (err) {
      console.error("Error parseando user del localStorage:", err);
      localStorage.removeItem("user");
    }
  }

  const token = getToken();
  if (!token) return null;

  try {
    const decoded = jwtDecode(token);
    const user = {
      username: decoded.sub,
      id: decoded.id,
    };
    localStorage.setItem("user", JSON.stringify(user));
    return user;
  } catch (error) {
    console.error("Error obteniendo usuario actual:", error);
    return null;
  }
};

// FUNCIÓN para hacer requests autenticados
export const authenticatedFetch = async (url, options = {}) => {
  const token = getToken();

  const headers = {
    'Content-Type': 'application/json',
    ...options.headers,
  };

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  return fetch(url, {
    ...options,
    headers,
  });
};
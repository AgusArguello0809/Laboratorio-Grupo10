import { createContext, useContext, useState, useEffect } from "react";

const UserContext = createContext();
export const useUser = () => useContext(UserContext);

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isInitialized, setIsInitialized] = useState(false);

  useEffect(() => {
    const storedUser = localStorage.getItem("usuario");
    if (storedUser) setUser(JSON.parse(storedUser));
    setIsInitialized(true);
  }, []);

  return (
    <UserContext.Provider value={{ user, setUser, isInitialized }}>
      {children}
    </UserContext.Provider>
  );
};